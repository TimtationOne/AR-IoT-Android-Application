package org.ariot.app.visualisation;

import android.app.Activity;
import android.widget.TextView;

import org.ariot.app.model.ArContent;

import java.util.ArrayList;

/**
 * Created by ebnert on 11.05.2017.
 */

public class GlobalVar {
    static Activity arActivity;
    static int  markerID;
    static String  markerText;
    public static String kanjiMarkerText = "KanjiMarker";
    public static String hiroMarkerText = "HiroMarker";
    public static String cacheFolder;
    public static ArrayList<ArContent> contentList = new ArrayList<ArContent>();
    public static int ViewId;

    public static ArrayList<TextView> tvList = new ArrayList<TextView>();

    public static Activity getArActivity() {
        return arActivity;
    }

    public static void setArActivity(Activity theArActivity) {
        arActivity = theArActivity;
    }

    public static int getMarkerID() {
        return markerID;
    }

    public static void setMarkerID(int markerID) {
        GlobalVar.markerID = markerID;
    }

    public static String getMarkerText() {
        return markerText;
    }

    public static void setMarkerText(String markerText) {
        GlobalVar.markerText = markerText;
    }


}
