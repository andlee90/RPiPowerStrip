package com.example.rpipowerstrip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

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

        class CheckChangeListener implements CompoundButton.OnCheckedChangeListener
        {
            private String mChannel;

            private CheckChangeListener(String channel)
            {
                this.mChannel = channel;
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (mHost != null && mUser != null && mPass != null)
                {
                    if (isChecked)
                    {
                        String command = "cd RelayControl/ && python relay_controller_android.py " + mChannel + " 0";
                        mConnectionManager = new ConnectionManager(mHost, mUser, mPass, command, new ConnectionManager.AsyncResponse(){

                            @Override
                            public void processFinish(String output)
                            {
                                Toast finishToast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT);
                                finishToast.show();
                            }
                        });
                        mConnectionManager.execute();
                    } else {

                        String command = "cd RelayControl/ && python relay_controller_android.py " + mChannel + " 1";
                        mConnectionManager = new ConnectionManager(mHost, mUser, mPass, command, new ConnectionManager.AsyncResponse(){

                            @Override
                            public void processFinish(String output)
                            {
                                Toast finishToast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT);
                                finishToast.show();
                            }
                        });
                        mConnectionManager.execute();
                    }
                }
            }
        }

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(false);
        switch1.setOnCheckedChangeListener(new CheckChangeListener("1"));

        Switch switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setChecked(false);
        switch2.setOnCheckedChangeListener(new CheckChangeListener("2"));

        Switch switch3 = (Switch) findViewById(R.id.switch3);
        switch3.setChecked(false);
        switch3.setOnCheckedChangeListener(new CheckChangeListener("3"));

        Switch switch4 = (Switch) findViewById(R.id.switch4);
        switch4.setChecked(false);
        switch4.setOnCheckedChangeListener(new CheckChangeListener("4"));

        Switch switch5 = (Switch) findViewById(R.id.switch5);
        switch5.setChecked(false);
        switch5.setOnCheckedChangeListener(new CheckChangeListener("5"));

        Switch switch6 = (Switch) findViewById(R.id.switch6);
        switch6.setChecked(false);
        switch6.setOnCheckedChangeListener(new CheckChangeListener("6"));

        Switch switch7 = (Switch) findViewById(R.id.switch7);
        switch7.setChecked(false);
        switch7.setOnCheckedChangeListener(new CheckChangeListener("7"));

        Switch switch8 = (Switch) findViewById(R.id.switch8);
        switch8.setChecked(false);
        switch8.setOnCheckedChangeListener(new CheckChangeListener("8"));
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
        if(item.getItemId() == R.id.settings)
        {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}