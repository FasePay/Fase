package Utils;

import android.content.Context;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.MerchantPaymentOption;
import com.citrus.sdk.payment.NetbankingOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.PaymentResponse;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sonu on 23-Nov-16.
 */

public class CitrusPay  {
    public CitrusClient citrusClient;
    private Context context;
    private JsonParsing jsonParsing;
    public CitrusPay(Context applicationContext) {
        this.context=applicationContext;
        jsonParsing=new JsonParsing(context);
        citrusClient=CitrusClient.getInstance(context);
        citrusClient.enableLog(true);
        citrusClient.init(
                "test-signup",
                "c78ec84e389814a05d3ae46546d16d2e",
                "test-signin",
                "52f7e15efd4208cf5345dd554443fd99",
                "testing",
                Environment.SANDBOX );
    }


    public CitrusClient getCitrusClient(){
        return citrusClient;
    }


    public ArrayList FetchPaymentOptionPgPayment(){
        final ArrayList[] arrayList = {new ArrayList()};
        citrusClient.getMerchantPaymentOptions(new Callback<MerchantPaymentOption>() {


            @Override
            public void success(MerchantPaymentOption merchantPaymentOption) {
                arrayList[0] = merchantPaymentOption.getNetbankingOptionList();//this will give you only bank list
                MyDebugClass.showLog("Arraylist", Arrays.toString(arrayList));
            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showToast(context,error.getMessage());
            }
        });


        return arrayList[0];
    }

    public ArrayList FetchPaymentOptionLoadMoney(){
        final ArrayList[] arrayList = {new ArrayList()};
        citrusClient.getLoadMoneyPaymentOptions(new Callback<MerchantPaymentOption>() {
            @Override
            public void success(MerchantPaymentOption loadMoneyPaymentOptions) {
                arrayList[0] = loadMoneyPaymentOptions.getNetbankingOptionList();
                //this will give you only bank list

            }

            @Override
            public void error(CitrusError error) {

            }
        });
        return arrayList[0];
    }



    public boolean loadmoneyIntoWallet(String name,String number,String cvv,String month,String year,String money) {
        final boolean[] b = new boolean[1];
        DebitCardOption debitCardOption
                = new DebitCardOption("Card Holder Name", "6762407506473539", "123", Month.getMonth("12"), Year.getYear("18"));

        Amount amount = new Amount(money);

        // Init Load Money PaymentType
        PaymentType.LoadMoney loadMoney = null;
        try {
            loadMoney = new PaymentType.LoadMoney(amount, debitCardOption);
        } catch (CitrusException e) {
            e.printStackTrace();
        }

        // Call Load Money
        citrusClient.simpliPay(loadMoney, new Callback<TransactionResponse>() {

            @Override
            public void success(TransactionResponse transactionResponse) {
                b[0] =true;
                MyDebugClass.showLog("wallet",transactionResponse.getJsonResponse());
                MyDebugClass.showToast(context,transactionResponse.getMessage());
            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showLog("walletError",error.getRawResponse()+"uygu");
                MyDebugClass.showToast(context,error.getMessage());
            }
        });
        return b[0];

    }

    public void loadMoneyFromNetBanking(String bankName,String CID,String rupee){
        NetbankingOption netbankingOption = new NetbankingOption(bankName ,CID);
        Amount amount = new Amount(rupee);

        // Init Net Banking PaymentType
        PaymentType.LoadMoney loadMoney = null;
        try {
            loadMoney = new PaymentType.LoadMoney(amount,netbankingOption);
        } catch (CitrusException e) {e.printStackTrace();

        }

        citrusClient.simpliPay(loadMoney, new Callback<TransactionResponse>() {

            @Override
            public void success(TransactionResponse transactionResponse) {
                MyDebugClass.showLog("wallet",transactionResponse.getJsonResponse());
                MyDebugClass.showToast(context,transactionResponse.getMessage());
            }

            @Override
            public void error(CitrusError error) {

                MyDebugClass.showToast(context,error.getMessage());
            }
        });
    }

    public void payFromWallet(String amount,String mobileNo){
        citrusClient.sendMoneyToMoblieNo(new Amount(amount), mobileNo, "My contribution", new Callback<PaymentResponse>() {
            @Override
            public void success(PaymentResponse paymentResponse) {
                MyDebugClass.showToast(context,paymentResponse.getMessage());
            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showToast(context,error.getMessage());
            }
        });

//or

    }
}
