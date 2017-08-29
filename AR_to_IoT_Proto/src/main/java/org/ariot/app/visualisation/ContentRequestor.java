package org.ariot.app.visualisation;

import android.util.Log;

import org.ariot.app.model.ArContent;

/**
 * Created by ebnert on 22.08.2017.
 */

public class ContentRequestor implements Runnable{
    ArContent content;
    boolean run=true;
    public ContentRequestor(ArContent content){
        this.content=content;
    }
    @Override
    public void run() {
        while (run){
            content.requestContent();
            Log.d("aCheckpoint","Content Requested");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
