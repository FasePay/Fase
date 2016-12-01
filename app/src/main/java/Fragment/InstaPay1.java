package Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.response.CitrusError;
import com.faseapp.faseapp.CardPay;
import com.faseapp.faseapp.R;

import Utils.CitrusPay;
import Utils.MyDebugClass;

/**
 * Created by Sonu on 29-Nov-16.
 */

public class InstaPay1 extends Fragment {
    public EditText payTo,mobileNo,amount;
    private CitrusPay citrusPay;
    private CitrusClient citrusClient;
    public AlertDialog alertDialog;
    public AlertDialog.Builder builder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button buttonBalance;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insta_pay1, container, false);
        Button button = (Button) view.findViewById(R.id.buttonBalance);
        payTo= (EditText) view.findViewById(R.id.editTextPayTo);
        citrusPay=new CitrusPay(getContext());
        citrusClient=citrusPay.getCitrusClient();
        citrusClient = CitrusClient.getInstance(getContext());
        mobileNo= (EditText) view.findViewById(R.id.editTextPayeeNumber);
        amount= (EditText) view.findViewById(R.id.editTextAmount);
        button.setSoundEffectsEnabled(false);
        Button buttonPayNow= (Button) view.findViewById(R.id.buttonPayNow);
        buttonBalance= (Button) view.findViewById(R.id.buttonBalance);
        getBal();
        buttonPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    rs=amount.getText().toString();
                  alertDialogForPayMoneyOption();

               // alertDialogProcessing();

                //startActivity(new Intent(getContext(),CardPay.class));
            }
        });
        buttonBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBal();

            }
        });

        return view;
    }

    private void getBal(){
        citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() {
            @Override
            public void success(Amount amount) {
                String s1="Balance =Rs ";
                String s2=String.valueOf(amount.getValueAsDouble());
                String s3=s1.concat(s2);
                buttonBalance.setText(s3);
                MyDebugClass.showToast(getContext(),"New balance is "+s3);
                MyDebugClass.showLog("getWallet",s3+"12");
            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showLog("balance",error.getMessage());
            }
        });
    }

    public void alertDialogForPayMoneyOption(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose)
                .setItems(R.array.paymentOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                               alertDialogProcessing();
                               citrusPay.payFromWallet(amount.getText().toString(),mobileNo.getText().toString());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getBal();
                                        alertDialog.hide();
                                        payTo.setText("");
                                        mobileNo.setText("");
                                        amount.setText("");
                                    }
                                }, 1500);

                                break;
                            case 1:
                                alertDialogForBankName();
                                break;
                            case 2:
                                startActivity(new Intent(getContext(),CardPay.class).putExtra("cardType","DC").putExtra("amount",amount.getText().toString()));
                                break;
                            case 3:
                                startActivity(new Intent(getContext(),CardPay.class).putExtra("cardType","CC").putExtra("amount",amount.getText().toString()));
                                break;
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void alertDialogForBankName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose)
                .setItems(R.array.bankList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citrusPay.loadMoneyFromNetBanking(getResources().getStringArray(R.array.bankList)[which],getResources().getStringArray(R.array.bankCID)[which],"10");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDebugClass.showToast(getContext(),"Please choose a mode to load balance");        // User cancelled the dialog
                    }
                });
        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public AlertDialog alertDialogProcessing(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.processing)
                .setCancelable(true);
        builder.create();

        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();
        alertDialog.getWindow().setLayout(700,250);


        return alertDialog;
    }

}
