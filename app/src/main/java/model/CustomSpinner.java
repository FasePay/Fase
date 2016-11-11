package model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by shashank on 11/11/2016.
 */

public class CustomSpinner extends Spinner {

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;
    private OnItemSelectedListener onItemSelectedListener;
    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // the Spinner constructors

    @Override
    public boolean performClick() {
        // register that the Spinner was opened so we have a status
        // indicator for the activity(which may lose focus for some other
        // reasons)
        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onSpinnerOpened();
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }
     public void setSelectedListener(OnItemSelectedListener onItemSelectedListener1)
     {
         onItemSelectedListener=onItemSelectedListener1;
     }
    /**
     * Propagate the closed Spinner event to the listener from outside.
     */
    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onSpinnerClosed();
        }
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     *
     * @return true for opened Spinner
     */
    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }
    public interface OnSpinnerEventsListener {

        void onSpinnerOpened();

        void onSpinnerClosed();

    }
    public interface OnItemSelectedListener{
        void onItemSelected();
    }
}