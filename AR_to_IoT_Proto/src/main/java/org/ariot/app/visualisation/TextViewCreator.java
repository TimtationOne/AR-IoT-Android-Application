package org.ariot.app.visualisation;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ariot.app.model.ArContent;
import org.artoolkit.ar.samples.AR_to_IoT_Proto.R;

/**
 * Created by ebnert on 22.08.2017.
 */

public class TextViewCreator implements Runnable {
    ArContent content;
    public TextViewCreator(ArContent content){
        this.content = content;
    }
    @Override
    public void run() {
        TextView myTv = new TextView(GlobalVar.getArActivity().getApplicationContext());
        myTv.setBackgroundColor(Color.GREEN);
        myTv.getBackground().setAlpha(127);
        myTv.setTextColor(Color.BLACK);
        myTv.setTextSize(16);

        myTv.setVisibility(View.VISIBLE );
        int id = View.generateViewId();
        myTv.setId(id);
        Log.d("aCheckpoint","New Id created: "+id);

        ((RelativeLayout)GlobalVar.getArActivity().findViewById(R.id.relLayout)).addView(myTv);
        content.tvInt = id;


    }
}