package navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.CreditCardOption;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;
import com.faseapp.faseapp.R;

/**
 * Created by shashank on 10/22/2016.
 */

public class CardAdd extends AppCompatActivity {
    EditText name,number,mnth,yr,cvv;
    TextInputLayout namt,numbert,mntht,yrt,cvvt;
    String holdername,cardnumber,cvvs,year,month;
    CitrusClient citrusclient;
    Context context;
    Button add;
    TextWatcher watch=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().trim().length()>0){
                namt.setError(null);
                numbert.setError(null);
                mntht.setError(null);
                yrt.setError(null);
                cvvt.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        context=this;
        namt=(TextInputLayout)findViewById(R.id.holdername);
        numbert=(TextInputLayout)findViewById(R.id.cardnumber);
        mntht=(TextInputLayout)findViewById(R.id.expirytextmnth);
        yrt=(TextInputLayout)findViewById(R.id.expirytextyr);
        cvvt=(TextInputLayout)findViewById(R.id.cvvtext);
        name=(EditText)findViewById(R.id.editTextholdername);
        number=(EditText)findViewById(R.id.editTextcardnumber);
        mnth=(EditText)findViewById(R.id.editTextexpirymnth);
        yr=(EditText)findViewById(R.id.editTextexpiryyr);
        cvv=(EditText)findViewById(R.id.editTextcvv);
        add=(Button)findViewById(R.id.button);
        holdername=name.getText().toString();
        cardnumber=number.getText().toString();
        cvvs=cvv.getText().toString();
        year=yr.getText().toString();
        month=mnth.getText().toString();
        name.addTextChangedListener(watch);
        number.addTextChangedListener(watch);
        mnth.addTextChangedListener(watch);
        yr.addTextChangedListener(watch);
        cvv.addTextChangedListener(watch);
         citrusclient=CitrusClient.getInstance(context);
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(holdername.trim().length()==0)
                   namt.setError("Enter holdername");
               if(cardnumber.trim().length()==0)
                   numbert.setError("Enter card number");
               if(year.trim().length()==0)
                   yrt.setError("Enter expiry year");
               if(month.trim().length()==0)
                   mntht.setError("Enter expiry month");
               if(cvvs.trim().length()==0)
                   cvvt.setError("Enter cvv");
               else {
                   citrusclient.savePaymentOption(new CreditCardOption(holdername, cardnumber, cvvs, Month.getMonth(month), Year.getYear(year)), new Callback<CitrusResponse>() {
                       @Override
                       public void success(CitrusResponse citrusResponse) {

                       }

                       @Override
                       public void error(CitrusError error) {

                       }
                   });
                   citrusclient.savePaymentOption(new DebitCardOption(holdername, cardnumber, cvvs, Month.getMonth(month), Year.getYear(year)), new Callback<CitrusResponse>() {
                       @Override
                       public void success(CitrusResponse citrusResponse) {

                       }

                       @Override
                       public void error(CitrusError error) {

                       }
                   });
               }
           }
       });
    }
}