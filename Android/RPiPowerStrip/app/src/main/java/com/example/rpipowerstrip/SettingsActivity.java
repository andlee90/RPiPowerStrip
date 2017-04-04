package com.example.rpipowerstrip;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

import static com.example.rpipowerstrip.MainActivity.PREFERENCES;
import static com.example.rpipowerstrip.R.id.button_accept;
import static com.example.rpipowerstrip.R.id.button_test;
import static com.example.rpipowerstrip.R.id.edit_host;
import static com.example.rpipowerstrip.R.id.edit_pass;
import static com.example.rpipowerstrip.R.id.edit_user;

/**
 * Created by andrewsmith on 3/19/17.
 */

public class SettingsActivity extends AppCompatActivity implements TextView.OnEditorActionListener,
        View.OnClickListener
{
    private String mHost;
    private String mUser;
    private String mPass;

    private EditText mHostField;
    private EditText mUserField;
    private EditText mPassField;

    public ConnectionManager mConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        mHost = settings.getString("host", "");
        mUser = settings.getString("user", "");
        mPass = settings.getString("pass", "");

        mHostField = (EditText) findViewById(edit_host);
        mHostField.setText(mHost);
        mHostField.setOnEditorActionListener(this);

        mUserField = (EditText) findViewById(edit_user);
        mUserField.setText(mUser);
        mUserField.setOnEditorActionListener(this);

        mPassField = (EditText) findViewById(edit_pass);
        mPassField.setOnEditorActionListener(this);

        Button acceptButton = (Button) findViewById(button_accept);
        acceptButton.setOnClickListener(this);

        Button testConnectionButton = (Button) findViewById(button_test);
        testConnectionButton.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        mHost = settings.getString("host", "");
        mUser = settings.getString("user", "");
        mPass = settings.getString("pass", "");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch(v.getId())
        {
            case R.id.edit_host:
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    mHost = mHostField.getText().toString();
                }
                break;

            case R.id.edit_user:
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    mUser = mUserField.getText().toString();
                }
                break;

            case R.id.edit_pass:
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    mPass = mPassField.getText().toString();
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.button_test)
        {
            if (mHost != null && mUser != null && mPass != null)
            {
                String[] colors = {"r", "g", "b", "y", "m", "c", "w"};
                int randomNum = ThreadLocalRandom.current().nextInt(0, 6 + 1);
                String command = "cd RGBControl/ && python rgb_led_mode_controller.py " + colors[randomNum];

                mConnectionManager = new ConnectionManager(mHost, mUser, mPass, command, new ConnectionManager.AsyncResponse(){

                    @Override
                    public void processFinish(String output)
                    {
                        Toast finishToast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG);
                        finishToast.show();
                    }
                });
                mConnectionManager.execute();
            }
            else
            {
                String toastContent = "Please fill out all fields and try again";
                Toast toast = Toast.makeText(getApplicationContext(), toastContent, Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else if (view.getId() == R.id.button_accept)
        {
            if (mHost != null && mUser != null && mPass != null)
            {
                SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("host", mHost);
                editor.putString("user", mUser);
                editor.putString("pass", mPass);
                editor.apply();
                finish();
            }
        }
    }
}
