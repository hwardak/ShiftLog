package hwardak.shiftlog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by HWardak on 2017-10-05.
 */

public class Toaster {
    public static void toastUp(Context context, String toastString){
        Toast toast = Toast.makeText(context, toastString, Toast.LENGTH_LONG);

        toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 250);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(25);
        messageTextView.setTextColor(Color.parseColor("GREEN"));

        toast.show();
    }


}
