package com.beacon.peng.yamba;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

/**
 * Created by huangpeng on 8/31/14.
 */
public class StatusFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "StatusActivity";

    private Button buttonTweet;
    private EditText editStatus;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        editStatus = (EditText) view.findViewById(R.id.editStatus);

        textCount = (TextView) view.findViewById(R.id.textCount);
        defaultTextColor = textCount.getTextColors().getDefaultColor();

        buttonTweet.setOnClickListener(this);

        editStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int count = 140 - editStatus.length(); //
                textCount.setText("you can input " + Integer.toString(count) + " more characters");
                textCount.setTextColor(Color.GREEN); //
                if (count < 10)
                    textCount.setTextColor(Color.RED);
                else
                    textCount.setTextColor(defaultTextColor);
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked with status: " + status);
        new PostTask().execute(status);
    }

    private final class PostTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());

            String username = prefs.getString("username", "");
            String password = prefs.getString("password", "");

            // Check that username and password are not empty
            // If empty, Toast a message to set login info and bounce to
            // SettingActivity
            // Hint: TextUtils.
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return "Please update your username and password";
            }

            YambaClient yambaClient = new YambaClient("student", "password");
            try {
                yambaClient.postStatus(strings[0]);
                return "Successfully posted: " + strings[0];
            } catch (YambaClientException e) {
                return "Failed to post to yamba service";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(StatusFragment.this.getActivity(), s, Toast.LENGTH_LONG);
        }
    }
}
