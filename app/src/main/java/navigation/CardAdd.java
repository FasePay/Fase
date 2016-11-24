package navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        context=this;
        name=(EditText)findViewById(R.id.editTextholdername);
        number=(EditText)findViewById(R.id.editTextcardnumber);
        mnth=(EditText)findViewById(R.id.editTextexpirymnth);
        yr=(EditText)findViewById(R.id.editTextexpiryyr);
        cvv=(EditText)findViewById(R.id.editTextcvv);
        add=(Button)findViewById(R.id.button);
        namt=(TextInputLayout)findViewById(R.id.holdername);
        numbert=(TextInputLayout)findViewById(R.id.cardnumber);
        mntht=(TextInputLayout)findViewById(R.id.expirytextmnth);
        yrt=(TextInputLayout)findViewById(R.id.expirytextyr);
        cvvt=(TextInputLayout)findViewById(R.id.cvvtext);
        holdername=name.getText().toString();
        cardnumber=number.getText().toString();
        cvvs=cvv.getText().toString();
        year=yr.getText().toString();
        month=mnth.getText().toString();
         citrusclient=CitrusClient.getInstance(context);
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
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
       });
    }
}