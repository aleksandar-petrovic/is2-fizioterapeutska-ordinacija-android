package com.example.aleksandar.fizioterapeutskaordinacija;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Pacijent;
import com.example.aleksandar.fizioterapeutskaordinacija.pacijent.WelcomePacijent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Start extends Activity {

    TextView txtUser;
    TextView txtPass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Molimo sacekajte...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtUser.setText("");
        txtPass.setText("");
    }

    public void loginClick(View view) {
        String user = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        if (user.equals("")) {
            txtUser.setError("Popunite polje");
            return;
        }
        if (pass.equals("")) {
            txtPass.setError("Popunite polje");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("username", txtUser.getText().toString());
        params.put("password", txtPass.getText().toString());
        invokeWebService(params);
    }

    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterPacijent.class);
        startActivity(intent);
    }

    private void pacijentLogin(Pacijent pacijent) {
        Intent intent = new Intent(this, WelcomePacijent.class);
        intent.putExtra("pacijent", pacijent);
        startActivity(intent);
    }


    public void invokeWebService(RequestParams params) {
        // Show Progress Dialog
        progressDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utility.SERVER_URL + "access/login", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Hide Progress Dialog
                progressDialog.hide();

                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (Exception e) {
                    // TODO
                }

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);

                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        if (obj.get("role").equals("pacijent")) {
                            Pacijent pacijent=Pacijent.getObjectFromJSON(new JSONObject(obj.getString("pacijent")));
                            pacijentLogin(pacijent);
                        }
                        // other roles
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Hide Progress Dialog
                progressDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Probajte ponovo", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Ne postoji internet konekcija", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
