package br.com.luan2.baseapp.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import br.com.luan2.baseapp.R;


/**
 * Created by Luan on 18/05/17.
 */

public class NewSliderView extends DefaultSliderView {

    public NewSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        target.setScaleType(ImageView.ScaleType.CENTER_CROP );
        bindEventAndShow(v, target);
        return super.getView();
    }}
