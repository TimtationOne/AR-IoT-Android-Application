package org.ariot.app.visualisation;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.artoolkit.ar.samples.AR_to_IoT_Proto.R;

/**
 * Created by ebnert on 22.08.2017.
 */

public class TextViewDeleter  implements Runnable {
    int tvId;

    public TextViewDeleter(int tvId){
        this.tvId = tvId;

    }



    @Override
    public void run() {
        TextView tv =  (TextView)GlobalVar.getArActivity().findViewById(tvId) ;
        tv.setText("o" );
        tv.setVisibility(View.GONE);
        ((RelativeLayout)GlobalVar.getArActivity().findViewById(R.id.relLayout)).removeView(tv); ;
    }

}

