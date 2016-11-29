package Utils;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;

import com.faseapp.faseapp.R;

/**
 * Created by Sonu on 24-Nov-16.
 */

public class DialogBox extends DialogFragment {
    public AlertDialog alertDialog;
    public Context context;
    public DialogBox(Context context){
        this.context=context;
    }
    public AlertDialog alertDialogProcessing(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.processing);
        builder.create();

        alertDialog = builder.create();
        return alertDialog;
    }


}
