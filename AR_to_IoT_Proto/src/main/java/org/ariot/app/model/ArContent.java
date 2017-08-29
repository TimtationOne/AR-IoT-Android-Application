package org.ariot.app.model;

import android.widget.TextView;

import org.ariot.app.http.HttpRequestor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ebnert on 22.08.2017.
 */

public class ArContent {
    int markerId;
    String markerPath;
    String middlewareId;
    String content;
    public int tvInt;
    boolean visible=false;
    public int getMarkerId() {
        return markerId;
    }

    public void setMarkerId(int markerId) {
        this.markerId = markerId;
    }

    public String getMarkerPath() {
        return markerPath;
    }

    public void setMarkerPath(String markerPath) {
        this.markerPath = markerPath;
    }

    public String getMiddlewareId() {
        return middlewareId;
    }

    public void setMiddlewareId(String middlewareId) {
        this.middlewareId = middlewareId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void requestContent() {
        this.content = getFormattedContent(HttpRequestor.sendMiddlewareRequest(middlewareId));
    }

    public String getFormattedContent(String input){
        String output;
        try {
            JSONObject object = new JSONObject(input);
            output = "ID: "+object.getString("id")+"\n";
            output += "Platform: "+object.getString("iotPlatform")+"\n";
            String content = object.getString("content");
            content = content.replace("{","");
            content = content.replace("}","");
            content = content.replace("\\","");
            content = content.replace("\",","\n");
            content = content.replace("\"","");
            output += content;

            JSONObject contentJson;
        } catch (JSONException e) {
            output = "Formatting Error: "+e.getMessage()+"\n";
            output += "Raw: "+input;
            e.printStackTrace();
        }
        return output;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
