package com.example.anjukakoralage.samanolaadmin.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.anjukakoralage.samanolaadmin.Adapter.AttckRegister_RecycleView_Adapter;
import com.example.anjukakoralage.samanolaadmin.AppController;
import com.example.anjukakoralage.samanolaadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAllActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private LinearLayout llRe;
    private AttckRegister_RecycleView_Adapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private HashMap<String, String> LoggedUser;
    private HashMap<String, String> UserSettings;
    private ArrayList<HashMap<String, String>> alRegisterItems;
    private TextView tvTotalCount;
    private int page = 0;
    private Switch scShowMyProjects;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        rv = (RecyclerView) findViewById(R.id.rv);
        llm = new LinearLayoutManager(ShowAllActivity.this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        tvTotalCount = (TextView) findViewById(R.id.tvTotalCount);
        llRe = (LinearLayout) findViewById(R.id.llRe);

        alRegisterItems = new ArrayList<>();
        showPDialog();
        GetRegisterData();

        llRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog))
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ShowAllActivity.super.onBackPressed();
                        System.exit(0);
                    }
                }).create().show();
    }


    private void showPDialog() {
        pDialog = new ProgressDialog(ShowAllActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading, Please wait...");
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void GetRegisterData() {

        String url = "https://pledge.savesripada.lk/api/profiles";
        JsonObjectRequest registerReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("CheckURL", response.toString());
                //alRegisterItems.clear();
                try {
                    if (!response.toString().equals("{}") && !response.toString().equals("") && response != null) {
                        JSONArray jaRegisterItems = response.has("data") ? response.getJSONArray("data") : new JSONArray();
                        if (!jaRegisterItems.toString().equals("[]")) {
                            for (int item = 0; item < jaRegisterItems.length(); item++) {
                                JSONObject joRegisterItem = jaRegisterItems.getJSONObject(item);
                                HashMap<String, String> hmRegisterItem = new HashMap<>();
                                hmRegisterItem.put("Id", (joRegisterItem.has("id") && !joRegisterItem.getString("id").equals("null")) ? joRegisterItem.getString("id") : "");
                                hmRegisterItem.put("Name", (joRegisterItem.has("name") && !joRegisterItem.getString("name").equals("null")) ? joRegisterItem.getString("name") : "");
                                hmRegisterItem.put("PhoneNo", (joRegisterItem.has("phoneNo") && !joRegisterItem.getString("phoneNo").equals("null")) ? joRegisterItem.getString("phoneNo") : "");
                                hmRegisterItem.put("Email", (joRegisterItem.has("email") && !joRegisterItem.getString("email").equals("null")) ? joRegisterItem.getString("email") : "");
                                hmRegisterItem.put("ImageStr", (joRegisterItem.has("image") && !joRegisterItem.getString("image").equals("null")) ? joRegisterItem.getString("image") : "");

                                alRegisterItems.add(hmRegisterItem);
                            }
                            adapter = new AttckRegister_RecycleView_Adapter(alRegisterItems);
                            rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            tvTotalCount.setText("Total Records " + alRegisterItems.size());
                            //loading = false;
                            //if (page == 1)
                            //    rv.scrollToPosition(9);
                            //if (page > 0)
                            //    rv.scrollToPosition((page * 10) - 1);
                        } else {
                            Snackbar.make(coordinatorLayout, "No data to display.", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Error, please contact administrator.", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    hidePDialog();
                    Snackbar.make(coordinatorLayout, "Error, please contact administrator.", Snackbar.LENGTH_LONG).show();
                }
                //loading = false;
                hidePDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //loading = false;
                System.out.println("CheckURLError ==>"+ volleyError.toString());
                hidePDialog();
                if (volleyError instanceof NetworkError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network not available, please try again later.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (volleyError instanceof TimeoutError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Timeout error, please try again later.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Error, please contact administrator.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        registerReq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(registerReq);
    }

}
