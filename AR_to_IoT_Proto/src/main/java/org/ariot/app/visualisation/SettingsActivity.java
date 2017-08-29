package org.ariot.app.visualisation;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import org.artoolkit.ar.samples.AR_to_IoT_Proto.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setContentView(R.layout.activity_settings);
    }
}
