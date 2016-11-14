package com.faseapp.faseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.CSVFile;
import model.CustomSpinner;

public class Register extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4,editText5;
    Button button;
    ArrayAdapter adapter;

    List<String> cities=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button= (Button) findViewById(R.id.buttonRegister);
        editText1= (EditText) findViewById(R.id.editText6);
        editText2= (EditText) findViewById(R.id.editText7);
        editText3= (EditText) findViewById(R.id.editText8);
        editText4= (EditText) findViewById(R.id.editText12);
        editText5= (EditText) findViewById(R.id.editText11);

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
                boolean flag1=(editText1.getText().toString()!=null) && (isAlpha(editText1.getText().toString().replaceAll("\\s+",""))) &&(editText1.getText().toString().replaceAll("\\s+","").length()>=3);
                boolean flag2=(editText2.getText().toString()!=null) && (isAlpha(editText2.getText().toString().replaceAll("\\s+",""))) &&(editText2.getText().toString().replaceAll("\\s+","").length()>=6);
                boolean flag3=(editText3.getText().toString()!=null)  &&(editText3.getText().toString().replaceAll("\\s+","").length()>=10);
                boolean flag4=(editText4.getText().toString()!=null)  &&(editText4.getText().toString().replaceAll("\\s+","").length()==6);
                boolean flag5=(editText5.getText().toString()!=null)  &&(editText5.getText().toString().replaceAll("\\s+","").length()==10);
                if(flag1 && flag2 && flag3 && flag4 && flag5) {
                    Intent intent=new Intent(Register.this,Merchant_account.class);
                    intent.putExtra("SIGNAL",true);
                    startActivity(intent);
                    finish();
                }
                else
                {
                  if(!flag1)
                     editText1.setError("Length should be atleast 3");
                    if(!flag2)
                        editText2.setError("Name should be of atleast 6 charactar length");
                    if(!flag3)
                       editText3.setError("Length should be atleast 10");
                    if(!flag4)
                       editText4.setError("Should consist of 6 digits");
                    if(!flag5) {
                       editText5.setError("Should consist of 10 digits");

                    }

                }
            }
        });
    }

    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

}
