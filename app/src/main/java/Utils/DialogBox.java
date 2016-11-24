package Utils;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;

import com.faseapp.faseapp.R;

/**
 * Created by Sonu on 24-Nov-16.
 */

public class DialogBox extends DialogFragment {

    public DialogBox(){

    }
    public void alertDialogForLoadMoneyOption(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose)
                .setItems(R.array.LoadMoneyOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDebugClass.showToast(getActivity(), String.valueOf(which));
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


}
