package com.beacon.peng.yamba;

import android.app.Activity;
import android.os.Bundle;


public class StatusActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1.
        //setContentView(R.layout.new_activity_status);

        // 2. alternative

        if (savedInstanceState == null) { //
            // Create a fragment
            StatusFragment fragment = new StatusFragment(); //

            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment,
                            fragment.getClass().getSimpleName())
                    .commit(); //

        }
    }
}
