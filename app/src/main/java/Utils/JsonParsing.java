package Utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sonu on 24-Nov-16.
 */

public class JsonParsing {
    private Context context;
    private JSONObject balance;


    public JsonParsing(Context context) {

        this.context=context;
    }
    public String parseJsonForBalance(String Json){
        JSONObject jsonObject=null;
        String string = null;

        try {
            jsonObject=new JSONObject(Json);


            balance=jsonObject.getJSONObject("Amount");
           string=balance.getString("value");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return string;
    }
}
