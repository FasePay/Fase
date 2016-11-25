package com.faseapp.faseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.CitrusUser;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.CreditCardOption;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;

import Utils.CitrusPay;
import Utils.MyDebugClass;

public class CardPay extends AppCompatActivity {
    private static final String BILL_URL = "https://fase.herokuapp.com/billgenerator";
    private CitrusClient citrusClient;
    private Button buttonProceed;
    public CitrusPay citrusPay;
    private String month,year;
    private EditText cardNumber,cardHolderName,cvv,expiryDate;
    String cardNum,cardHolder,cvvNo,expiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pay);
        citrusPay=new CitrusPay(getApplicationContext());
        final CitrusPay citrusPay=new CitrusPay(getApplicationContext());
        citrusClient=citrusPay.getCitrusClient();
        cardNumber= (EditText) findViewById(R.id.editTextCardNumber);
        cardHolderName= (EditText) findViewById(R.id.editTextCardHolderNumber);
        cvv= (EditText) findViewById(R.id.editTextCvv);
        expiryDate= (EditText) findViewById(R.id.editTextExpiryDate);
        buttonProceed= (Button) findViewById(R.id.buttonProceed);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTextField()){
                    convertMonthAndYear(expiryDate.getText().toString());
                   //citrusPay.loadmoneyIntoWallet(cardHolderName.getText().toString(),cardNumber.getText().toString(),cvv.getText().toString(),month,year,"10");
                    //paymentUsingType("","balance");

                }

            }
        });
    }

    private void convertMonthAndYear(String expiryDate) {
        String[] parts = expiryDate.split("/");
        String part1 = parts[0]; // 004
        String part2 = parts[1];
        month=part1;
        year=part2;
    }

    public boolean checkTextField(){

        if(TextUtils.isEmpty(cardNumber.getText())||cardNumber.getText().length()<16){
            MyDebugClass.showToast(getApplicationContext(),"Please enter valid card number");
            return false;
        }
        else if(cvv.getText().length()<3){
            MyDebugClass.showToast(getApplicationContext(),"Please enter valid cvv number");
            return false;
        }
        else if(expiryDate.getText().length()<5){
            MyDebugClass.showToast(getApplicationContext(),"Please enter valid date");
            return false;
        }
        else if(TextUtils.isEmpty(cardHolderName.getText())||TextUtils.isEmpty(cvv.getText())||TextUtils.isEmpty(expiryDate.getText()))
        {
            MyDebugClass.showToast(getApplicationContext(),"Please fill all text field");
            return false;
        }
        else {
            return true;
        }


    }

    private void paymentUsingType(String type,String string) {
        PaymentType.PGPayment pgPayment = null;
        DebitCardOption debitCardOption = new DebitCardOption("", "", "", Month.getMonth(""), Year.getYear(""));
        CreditCardOption creditCardOption=new CreditCardOption("","","",Month.getMonth(""),Year.getYear(""));
        Amount amount = new Amount(string);

// Init PaymentType


        try {
           pgPayment = new PaymentType.PGPayment(amount, BILL_URL, debitCardOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
        } catch (CitrusException e) {
            e.printStackTrace();
        }

        citrusClient.simpliPay(pgPayment, new Callback<TransactionResponse>()

        {

            @Override
            public void success(TransactionResponse transactionResponse) {

            }

            @Override

            public void error(CitrusError error) { }

        });


    }



}
