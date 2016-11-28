package com.faseapp.faseapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.response.CitrusError;

import Utils.CitrusPay;
import Utils.MyDebugClass;

/**
 * Created by amit on 14/11/16.
 */

public class Merchant_account extends AppCompatActivity {
    Button save;
    CitrusPay citrusPay;
    private String s2;
    CitrusClient citrusClient;
    EditText editText1,editText2,editText3,editText4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citrusPay=new CitrusPay(getApplicationContext());
        citrusClient=citrusPay.getCitrusClient();
        setContentView(R.layout.merchant_account);
        editText1= (EditText) findViewById(R.id.editTextbank);
        editText2= (EditText) findViewById(R.id.editTextacc);
        editText3= (EditText) findViewById(R.id.editTextifsc);
        editText4= (EditText) findViewById(R.id.editTextholder);
        save=(Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean flag1=(editText1.getText().toString()!=null) && (isAlpha(editText1.getText().toString().replaceAll("\\s+",""))) &&(editText1.getText().toString().replaceAll("\\s+","").length()>=4);
                boolean flag2=(editText2.getText().toString()!=null) &&(editText2.getText().toString().replaceAll("\\s+","").length()==16);
                boolean flag3=(editText3.getText().toString()!=null) && (isAlphaNumeric(editText3.getText().toString().replaceAll("\\s+",""))) &&(editText3.getText().toString().replaceAll("\\s+","").length()==11);
                boolean flag4=(editText4.getText().toString()!=null) && (isAlpha(editText4.getText().toString().replaceAll("\\s+",""))) &&(editText4.getText().toString().replaceAll("\\s+","").length()>=6);
                if(flag1 && flag2 && flag3 && flag4) {
                    Toast.makeText(getApplicationContext(),"Details entered Successfully",Toast.LENGTH_LONG).show();

                    citrusPay.cashOutWithdraw(getBalance(),editText2.getText().toString(),editText3.getText().toString(),editText4.getText().toString());
//                    editText1.setText("");
//                    editText2.setText("");
//                    editText3.setText("");
//                    editText4.setText("");
                }
                else
                {
                    if(!flag1)
                        editText1.setError("Bank Name should be atleast 4 charactars long");
                    if(!flag2)
                        editText2.setError("Should consist of 16 digits");
                    if(!flag3)
                        editText3.setError("Length should be 11 with alphanumeric charactars");
                    if(!flag4)
                        editText4.setError("Name should be atleast 6 charactar long");

                }
            }
        });

    }

    private String getBalance() {
        citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() {
            @Override
            public void success(Amount amount) {
                s2=String.valueOf(amount.getValueAsDouble());

            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showLog("balance",error.getMessage());
            }
        });
        return s2;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public boolean isAlphaNumeric(String s){

        return s.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$");
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
