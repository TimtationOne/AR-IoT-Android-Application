package org.ariot.app.visualisation;

import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.artoolkit.ar.samples.AR_to_IoT_Proto.R;

/**
 * Created by ebnert on 22.08.2017.
 */

public class TextViewUpdater implements Runnable {
    int tvId;
    float[] transMatrix;
    String text;

    public TextViewUpdater(int tvId, float[] transMatrix, String text){
        this.tvId = tvId;
        this.transMatrix = transMatrix;
        this.text = text;
    }



    @Override
    public void run() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int x = (int) ((transMatrix[12] + 100) * 9.6);
        int y = (int) ((-transMatrix[13] + 100) * 5.4);
        params.setMargins(x, y, 0, 0);
        TextView tv =  (TextView)GlobalVar.getArActivity().findViewById(R.id.relLayout).findViewById(tvId) ;
        if (tv!=null){
            tv.setLayoutParams(params);
            tv.setText(text);
        } else{
            Log.d("aCheckpoint","Text View Not Found");
            Log.d("aCheckpoint","Text View ID: "+tvId);
        }
    }

}