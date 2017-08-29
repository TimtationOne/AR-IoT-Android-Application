/*
 *  SimpleRenderer.java
 *  ARToolKit5
 *
 *  Disclaimer: IMPORTANT:  This Daqri software is supplied to you by Daqri
 *  LLC ("Daqri") in consideration of your agreement to the following
 *  terms, and your use, installation, modification or redistribution of
 *  this Daqri software constitutes acceptance of these terms.  If you do
 *  not agree with these terms, please do not use, install, modify or
 *  redistribute this Daqri software.
 *
 *  In consideration of your agreement to abide by the following terms, and
 *  subject to these terms, Daqri grants you a personal, non-exclusive
 *  license, under Daqri's copyrights in this original Daqri software (the
 *  "Daqri Software"), to use, reproduce, modify and redistribute the Daqri
 *  Software, with or without modifications, in source and/or binary forms;
 *  provided that if you redistribute the Daqri Software in its entirety and
 *  without modifications, you must retain this notice and the following
 *  text and disclaimers in all such redistributions of the Daqri Software.
 *  Neither the name, trademarks, service marks or logos of Daqri LLC may
 *  be used to endorse or promote products derived from the Daqri Software
 *  without specific prior written permission from Daqri.  Except as
 *  expressly stated in this notice, no other rights or licenses, express or
 *  implied, are granted by Daqri herein, including but not limited to any
 *  patent rights that may be infringed by your derivative works or by other
 *  works in which the Daqri Software may be incorporated.
 *
 *  The Daqri Software is provided by Daqri on an "AS IS" basis.  DAQRI
 *  MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 *  THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE, REGARDING THE DAQRI SOFTWARE OR ITS USE AND
 *  OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 *
 *  IN NO EVENT SHALL DAQRI BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 *  MODIFICATION AND/OR DISTRIBUTION OF THE DAQRI SOFTWARE, HOWEVER CAUSED
 *  AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 *  STRICT LIABILITY OR OTHERWISE, EVEN IF DAQRI HAS BEEN ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 *  Copyright 2015 Daqri, LLC.
 *  Copyright 2011-2015 ARToolworks, Inc.
 *
 *  Author(s): Julian Looser, Philip Lamb
 *
 */

package org.ariot.app.augmentedreality;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import org.ariot.app.http.HttpRequestor;
import org.ariot.app.model.ArContent;
import org.ariot.app.visualisation.RenderText;
import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.NativeInterface;
import org.artoolkit.ar.base.rendering.ARRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ariot.app.visualisation.GlobalVar;

import javax.microedition.khronos.opengles.GL10;

/**
 * A very simple Renderer that adds a marker and draws a cube on it.
 */
public class ARTracker extends ARRenderer {

    View v;

    public void getMarkerInfos(){
        ARMainActivity ars = (ARMainActivity) GlobalVar.getArActivity();
        Log.d("Checkpoint3","Configure Marker Infos");
        String deviceInfo = HttpRequestor.getDeviceInfo();
        try {
            JSONArray deviceJson = new JSONArray(deviceInfo) ;
            for (int loopVar=0;loopVar< deviceJson.length(); loopVar++ ){
                Log.d("Checkpoint3","Device Infos Downloaded" );
                JSONObject tempJson = deviceJson.getJSONObject(loopVar);
                ArContent tempAr = new ArContent();
                tempAr.setMarkerId(-1);
                tempAr.setMiddlewareId(tempJson.getString("deviceId"));
                tempAr.setContent(tempJson.getString("deviceId"));
                tempAr.setMarkerPath("single_barcode;"+ tempJson.getString("markerId") +";80");
                GlobalVar.contentList.add(tempAr);
                Log.d("Checkpoint3","Device " + tempJson.getString("deviceId") + " added!" );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void downloadMarker(String url, String fileName){
        Log.d("Checkpoint3","Data Folder: "+Environment.getDataDirectory()  );
        ARMainActivity ars = (ARMainActivity) GlobalVar.getArActivity();
        ars.download(url, fileName);
        Log.d("Checkpoint3","Downloaded"  );
    }

    /**
     * Markers can be configured here.
     */

    private int markerID = -1;
    @Override
    public boolean configureARScene() {
        Log.d("Checkpoint","Configure AR Scene");
        getMarkerInfos();
        for (int i = 0; i< GlobalVar.contentList.size(); i++ ){
            NativeInterface.arwSetPatternDetectionMode(NativeInterface.AR_MATRIX_CODE_DETECTION);
            NativeInterface.arwSetMatrixCodeType(NativeInterface.AR_MATRIX_CODE_5x5);
            GlobalVar.contentList.get(i).setMarkerId(ARToolKit.getInstance().addMarker(GlobalVar.contentList.get(i).getMarkerPath()));
            Log.d("Checkpoint","Loaded with id: "+GlobalVar.contentList.get(i).getMarkerId());
            if(GlobalVar.contentList.get(i).getMarkerId()<0){
                return false;
            }
        }
        return true;
    }

    /**
     * Override the draw function from ARRenderer.
     */
    @Override
    public void draw(GL10 gl) {

        ARMainActivity ars = (ARMainActivity) GlobalVar.getArActivity();
        for (int i = 0; i< GlobalVar.contentList.size(); i++ ){
            if (ARToolKit.getInstance().queryMarkerVisible(GlobalVar.contentList.get(i).getMarkerId())){
                Log.d("Checkpoint","Recognized Marker " + GlobalVar.contentList.get(i).getMarkerId());
                if(GlobalVar.contentList.get(i).isVisible()==false){
                    GlobalVar.contentList.get(i).setVisible(true);
                    Thread t = new Thread(new RenderText(GlobalVar.contentList.get(i)));
                    t.start();
                }

                //ars.positionDataTextCommon(GlobalVar.contentList.get(i));
            } else{
                //ars.deleteText(GlobalVar.contentList.get(i));
            }
        }

    }

}