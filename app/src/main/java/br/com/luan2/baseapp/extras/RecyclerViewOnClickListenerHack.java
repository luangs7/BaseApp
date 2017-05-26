package br.com.luan2.baseapp.extras;

import android.view.View;

/**
 * Created by anderson on 06/06/2015.
 */
public interface RecyclerViewOnClickListenerHack {
    public void onClickListener(View view, int position);
    public void onClickListener(View view, Object object, int position);
    //public void onLongPressClickListener(View view, int position);
   // public void onActionClickListener(View view, int position, int action);
}
