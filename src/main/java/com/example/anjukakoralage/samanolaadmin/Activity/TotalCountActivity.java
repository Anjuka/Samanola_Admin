package com.example.anjukakoralage.samanolaadmin.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class TotalCountActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private TextView tvCount;
    private Button btnMore;
    private String Count;
    private ArrayList<HashMap<String, String>> alRegisterItems;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_count);

        alRegisterItems = new ArrayList<>();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        tvCount = (TextView) findViewById(R.id.tvCount);
        btnMore = (Button) findViewById(R.id.btnMore);

        showPDialog();
        GetSMSDetails();

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showPDialog() {
        pDialog = new ProgressDialog(TotalCountActivity.this);
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

    public void GetSMSDetails() {

        String url = "https://pledge.savesripada.lk/api/singleprofile";
        JsonObjectRequest registerReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("CheckURL", response.toString());
                System.out.println("============>" + response.toString());

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

                                Count = joRegisterItem.optString("id");
                                tvCount.setText(Count);

                                hidePDialog();

                            }

                        } else {
                            hidePDialog();
                            Snackbar.make(coordinatorLayout, "No data to display.", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        hidePDialog();
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
                System.out.println("CheckURLError ==>" + volleyError.toString());
                //    hidePDialog();
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
        registerReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(registerReq);
    }

}
