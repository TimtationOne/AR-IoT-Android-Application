package org.ariot.app.visualisation;

import android.util.Log;
import android.widget.TextView;

import org.ariot.app.model.ArContent;
import org.artoolkit.ar.base.ARToolKit;
import org.ariot.app.augmentedreality.ARMainActivity;

/**
 * Created by Tim Ebner on 12.05.2017.
 */

public class RenderText implements Runnable {
    ArContent content;
    TextView contentTV;
    ARMainActivity ars;


    public RenderText(ArContent content){
        this.content = content;
    }



    @Override
    public void run() {
        ars = (ARMainActivity) GlobalVar.getArActivity();
        ContentRequestor contentReq = new ContentRequestor(content);
        Thread updThread = new Thread(contentReq);
        updThread.start();
        TextViewCreator tvCreator = new TextViewCreator(content);
        ars.runOnUiThread(tvCreator);
        while(ARToolKit.getInstance().queryMarkerVisible(content.getMarkerId() )) {
            float[] pm = ARToolKit.getInstance().queryMarkerTransformation(content.getMarkerId());
            if (pm!=null) {
                Log.d("Checkpoint","Marker still visible");
                /*contentTV.setLayoutParams(params);
                contentTV.setText(content.getContent() );*/
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextViewUpdater tvUpdater = new TextViewUpdater(content.tvInt ,pm,content.getContent());
                ars.runOnUiThread(tvUpdater);
                Log.d("Checkpoint","Text Set");
            }
        }
        content.setVisible(false);
        TextViewDeleter tvDeleter = new TextViewDeleter(content.tvInt);
        ars.runOnUiThread(tvDeleter);
        contentReq.setRun(false);

    }

}
