package com.example.rpipowerstrip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import static com.example.rpipowerstrip.R.id.settings;

public class MainActivity extends AppCompatActivity
{
    public static final String PREFERENCES = "Preferences";

    private String mHost;
    private String mUser;
    private String mPass;

    public ConnectionManager mConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        mHost = settings.getString("host", "");
        mUser = settings.getString("user", "");
        mPass = settings.getString("pass", "");

        boolean mSwitchState1 = settings.getBoolean("ss1", false);
        boolean mSwitchState2 = settings.getBoolean("ss2", false);
        boolean mSwitchState3 = settings.getBoolean("ss3", false);
        boolean mSwitchState4 = settings.getBoolean("ss4", false);
        boolean mSwitchState5 = settings.getBoolean("ss5", false);
        boolean mSwitchState6 = settings.getBoolean("ss6", false);
        boolean mSwitchState7 = settings.getBoolean("ss7", false);
        boolean mSwitchState8 = settings.getBoolean("ss8", false);

        ProgressBar spinner = (ProgressBar) findViewById(R.id.progress_bar_status);
        spinner.setVisibility(View.GONE);

        TextView statusView = (TextView) findViewById(R.id.text_status);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(mSwitchState1);
        switch1.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "1"));

        Switch switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setChecked(mSwitchState2);
        switch2.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "2"));

        Switch switch3 = (Switch) findViewById(R.id.switch3);
        switch3.setChecked(mSwitchState3);
        switch3.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "3"));

        Switch switch4 = (Switch) findViewById(R.id.switch4);
        switch4.setChecked(mSwitchState4);
        switch4.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "4"));

        Switch switch5 = (Switch) findViewById(R.id.switch5);
        switch5.setChecked(mSwitchState5);
        switch5.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "5"));

        Switch switch6 = (Switch) findViewById(R.id.switch6);
        switch6.setChecked(mSwitchState6);
        switch6.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "6"));

        Switch switch7 = (Switch) findViewById(R.id.switch7);
        switch7.setChecked(mSwitchState7);
        switch7.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "7"));

        Switch switch8 = (Switch) findViewById(R.id.switch8);
        switch8.setChecked(mSwitchState8);
        switch8.setOnCheckedChangeListener(new SwitchCheckChangeListener(this, mConnectionManager,
                spinner, statusView, "8"));
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
    protected void onStop()
    {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("host", mHost);
        editor.putString("user", mUser);
        editor.putString("pass", mPass);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == settings)
        {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

