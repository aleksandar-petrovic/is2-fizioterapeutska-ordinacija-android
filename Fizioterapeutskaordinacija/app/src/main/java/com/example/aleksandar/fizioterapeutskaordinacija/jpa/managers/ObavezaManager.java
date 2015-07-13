package com.example.aleksandar.fizioterapeutskaordinacija.jpa.managers;

import android.content.Context;
import android.widget.Toast;

import com.example.aleksandar.fizioterapeutskaordinacija.Utility;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Obaveza;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Pacijent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Aleksandar on 13.7.2015..
 */
public class ObavezaManager {

    public static List<Obaveza> getPacijentObavezeForDate(final Context context, Pacijent pacijent, String date) {
        final List<Obaveza> list = new ArrayList<>();

        final AtomicInteger atomicInteger = new AtomicInteger();

        final SyncHttpClient client = new SyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("id", pacijent.getId());
        params.put("date", date);

        new Thread(new Runnable() {
            @Override
            public void run() {
                client.get(Utility.SERVER_URL + "pacijent/obaveza", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String response = "";
                        try {
                            response = new String(responseBody, "UTF-8");
                        } catch (Exception e) {
                            // TODO
                        }

                        try {

                            JSONObject obj = new JSONObject(response);
                            int size = obj.getInt("size");

                            for (int i = 0; i < size; i++) {
                                Obaveza obaveza = Obaveza.getObjectFromJSON(new JSONObject(obj.getString("" + i)));
                                list.add(obaveza);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        } finally {
                            atomicInteger.incrementAndGet();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(context, "Pokusajte ponovo", Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(context, "Ne postoji internet konekcija", Toast.LENGTH_LONG).show();
                        }

                        atomicInteger.incrementAndGet();
                    }
                });
            }
        }).start();

        while (!atomicInteger.compareAndSet(1, 2)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

}
