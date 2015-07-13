package com.example.aleksandar.fizioterapeutskaordinacija;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Fizijatar;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Fizioterapeut;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RegisterPacijent extends Activity {

    TextView txtUser;
    TextView txtPass1;
    TextView txtPass2;
    Spinner spinnerFizijatar;
    Spinner spinnerFizioterapeut;
    ProgressDialog progressDialog;
    List<Fizijatar> listFizijatar;
    List<Fizioterapeut> listFizioterapeut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pacijent);

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass1 = (TextView) findViewById(R.id.txtPass1);
        txtPass2 = (TextView) findViewById(R.id.txtPass2);
        spinnerFizijatar = (Spinner) findViewById(R.id.spinnerFizijatar);
        spinnerFizioterapeut = (Spinner) findViewById(R.id.spinnerFizioterapeut);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Molimo sacekajte...");
        progressDialog.setCancelable(false);

        listFizijatar = new ArrayList<>();
        listFizioterapeut = new ArrayList<>();

        initSpinners();
    }

    private void initSpinners() {
        getAllFizijatar();
        getAllFizioterapeut();
    }

    private void getAllFizijatar() {
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, Utility.SERVER_URL + "all/fizijatar", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.hide();

                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (Exception e) {
                    // TODO
                }

                List<String> list = new ArrayList<String>();

                try {
                    JSONObject json = new JSONObject(response);
                    int size = json.getInt("size");
                    for (int i = 0; i < size; i++) {
                        String str = json.getString("" + i);
                        JSONObject obj = new JSONObject(str);
                        Fizijatar fizijatar = new Fizijatar(obj.getInt("idFizijatar"), obj.getString("ime"), obj.getString("prezime"), obj.getString("spec"));
                        listFizijatar.add(fizijatar);
                        list.add(fizijatar.toString());

                        //System.out.println("Fizijatar: " + fizijatar.toString());
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // init spinner
                ArrayAdapter<String> adapterFizijatar = new ArrayAdapter<String>(RegisterPacijent.this,
                        android.R.layout.simple_spinner_item, list);
                adapterFizijatar
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFizijatar.setAdapter(adapterFizijatar);
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
                    Toast.makeText(getApplicationContext(), "Pokusajte ponovo", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Ne postoji internet konekcija", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getAllFizioterapeut() {
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, Utility.SERVER_URL + "all/fizioterapeut", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.hide();

                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (Exception e) {
                    // TODO
                }

                List<String> list = new LinkedList<>();

                try {
                    JSONObject json = new JSONObject(response);

                    int size = json.getInt("size");
                    for (int i = 0; i < size; i++) {
                        String str = json.getString("" + i);
                        JSONObject obj = new JSONObject(str);
                        Fizioterapeut fizioterapeut = new Fizioterapeut(obj.getInt("idFizioterapeut"), obj.getString("ime"), obj.getString("prezime"), obj.getString("spec"));
                        listFizioterapeut.add(fizioterapeut);
                        list.add(fizioterapeut.toString());

                        //System.out.println("Fizioterapeut: " + fizioterapeut.toString());
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // init spinner
                ArrayAdapter<String> adapterFizioterapeut = new ArrayAdapter<String>(RegisterPacijent.this,
                        android.R.layout.simple_spinner_item, list);
                adapterFizioterapeut
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFizioterapeut.setAdapter(adapterFizioterapeut);
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
                    Toast.makeText(getApplicationContext(), "Pokusajte ponovo", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Ne postoji internet konekcija", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registerButton(View view) {
        String user = txtUser.getText().toString();
        String pass1 = txtPass1.getText().toString();
        String pass2 = txtPass2.getText().toString();

        if (user.equals("")) {
            txtUser.setError("Popunite polje");
            return;
        }
        if (pass1.equals("")) {
            txtPass1.setError("Popunite polje");
            return;
        }
        if (pass2.equals("")) {
            txtPass2.setError("Popunite polje");
            return;
        }
        if (!pass1.equals(pass2)) {
            Toast.makeText(getApplicationContext(), "Sifre se ne poklapaju", Toast.LENGTH_LONG).show();
            return;
        }

        final int idFizijatar = listFizijatar.get((int) spinnerFizijatar.getSelectedItemId()).getId();
        final int idFizioterapeut = listFizioterapeut.get((int) spinnerFizioterapeut.getSelectedItemId()).getId();

        RequestParams params = new RequestParams();
        params.put("username", txtUser.getText());
        params.put("password", txtPass1.getText());
        params.put("fizijatarId", idFizijatar);
        params.put("fizioterapeutId", idFizioterapeut);

        // show progress bar
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, Utility.SERVER_URL + "access/register/pacijent", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.hide();

                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (Exception e) {
                    // TODO
                }

                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getBoolean("status"))
                        Toast.makeText(getApplicationContext(), "Uspesna registracija", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Neuspesna registracija", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Pokusajte ponovo", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Ne postoji internet konekcija", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
