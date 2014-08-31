package com.beacon.peng.yamba;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by huangpeng on 8/31/14.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) { //
            // Create a fragment
            SettingsFragment fragment = new SettingsFragment(); //

            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment,
                            fragment.getClass().getSimpleName())
                    .commit(); //

        }
    }
}
