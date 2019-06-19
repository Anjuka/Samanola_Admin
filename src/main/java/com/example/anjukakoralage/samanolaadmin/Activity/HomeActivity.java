package com.example.anjukakoralage.samanolaadmin.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.anjukakoralage.samanolaadmin.R;
import com.example.anjukakoralage.samanolaadmin.SupportActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText etuserName, etPassword;
    private Button btnLogin;
    private LinearLayout llSuport;
    private String uName,pWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etuserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        llSuport = (LinearLayout) findViewById(R.id.llSupport);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uName = etuserName.getText().toString();
                pWord = etPassword.getText().toString();

                if (uName.equalsIgnoreCase("") || pWord.equalsIgnoreCase("")){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please fill the relevant details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                if (uName.equalsIgnoreCase("admin") && pWord.equalsIgnoreCase("123456")){
                    Intent intent = new Intent(getApplicationContext(), TotalCountActivity.class);
                    startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Input does not match", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
             }
        });

        llSuport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
            }
        });


    }
}
