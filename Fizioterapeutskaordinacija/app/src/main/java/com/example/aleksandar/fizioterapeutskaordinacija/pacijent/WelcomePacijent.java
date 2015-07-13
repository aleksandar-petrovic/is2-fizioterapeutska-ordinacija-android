package com.example.aleksandar.fizioterapeutskaordinacija.pacijent;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aleksandar.fizioterapeutskaordinacija.R;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.managers.ObavezaManager;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Obaveza;
import com.example.aleksandar.fizioterapeutskaordinacija.jpa.model.Pacijent;

import java.util.List;

public class WelcomePacijent extends Activity implements CalendarView.OnDateChangeListener {

    Pacijent pacijent;

    Button btnAddEvent;
    CalendarView calendar;
    ProgressDialog progressDialog;

    LinearLayout layoutEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pacijent);

        pacijent = (Pacijent) getIntent().getSerializableExtra("pacijent");

        TextView txtUser = (TextView) findViewById(R.id.txtWelcome);
        txtUser.setText(pacijent.getIme() + " " + pacijent.getPrezime() + ", dobrodosli");

        calendar = (CalendarView) findViewById(R.id.calendarView);
        layoutEvents = (LinearLayout) findViewById(R.id.layoutEvents);

        calendar.setOnDateChangeListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Molimo sacekajte...");
        progressDialog.setCancelable(false);

    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        String date = year + "-" + ++month + "-" + dayOfMonth;
        Toast.makeText(this, "Datum: " + date, Toast.LENGTH_LONG).show();
        layoutEvents.removeAllViews();
        showPacijentObavezeForDate(pacijent, date);
    }

    private void showPacijentObavezeForDate(Pacijent pacijent, String date) {
        progressDialog.show();

        List<Obaveza> list = ObavezaManager.getPacijentObavezeForDate(getApplicationContext(), pacijent, date);
        for (Obaveza obaveza : list) {
            addEvent(obaveza);
        }

        progressDialog.hide();
    }

    private void addEvent(Obaveza obaveza) {


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        LinearLayout layoutEvent = new LinearLayout(this);
        layoutEvent.setBackgroundColor(getResources().getColor(R.color.backgroundEvent));
        layoutEvent.setLayoutParams(layoutParams);
        layoutEvent.setPadding(0, 0, 0, 10);
        layoutEvent.setOrientation(LinearLayout.VERTICAL);

        // obaveza add to layoutEvent
        TextView txtNaziv = new TextView(this);
        txtNaziv.setText(obaveza.getNaziv());
        TextView txtOpis = new TextView(this);
        txtOpis.setText(obaveza.getOpis());

        layoutEvent.addView(txtNaziv);
        layoutEvent.addView(txtOpis);

        layoutEvents.addView(layoutEvent);
    }


}
