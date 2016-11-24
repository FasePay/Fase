package Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sonu on 26-Oct-16.
 */

public class MyDebugClass {
    public static void showToast(Context context, String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String tag,String string){
        Log.d(tag,string);
    }
}
