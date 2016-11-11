package com.faseapp.faseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.CSVFile;
import model.CustomSpinner;

public class Register extends AppCompatActivity {
    Button button;
    ArrayAdapter adapter;
    List<String> cities=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button= (Button) findViewById(R.id.buttonRegister);
        cities.add("City");

        InputStream inputStream = getResources().openRawResource(R.raw.india_state_city_database_list);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String> scoreList = csvFile.readStates();

        final ArrayAdapter adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item_selected,scoreList);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);



        final CustomSpinner spinner = (CustomSpinner)findViewById(R.id.spinner);

        spinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener(){

            @Override
            public void onSpinnerOpened() {
                spinner.setSelected(true);
            }

            @Override
            public void onSpinnerClosed() {
           spinner.setSelected(false);
            }
        });

        final CustomSpinner spinner1 = (CustomSpinner) findViewById(R.id.spinner_state);
        spinner1.setAdapter(adapter1);


        spinner1.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener(){

            @Override
            public void onSpinnerOpened() {
          spinner1.setSelected(true);
            }

            @Override
            public void onSpinnerClosed() {
                spinner1.setSelected(false);
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InputStream inputStreams = getResources().openRawResource(R.raw.india_state_city_database_list);
                final CSVFile csvFiles = new CSVFile(inputStreams);
                cities=csvFiles.readCities(spinner1.getSelectedItem().toString());
                adapter = new ArrayAdapter<>(Register.this, R.layout.spinner_item_selected, cities);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
     /*   spinner1.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected() {
                Toast.makeText(getApplicationContext(),spinner1.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                cities=csvFiles.readCities(spinner1.getSelectedItem().toString());
                adapter.setNotifyOnChange(true);
            }

        });*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting activity
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }


}
