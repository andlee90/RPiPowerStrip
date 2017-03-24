package com.example.rpipowerstrip;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.rpipowerstrip.MainActivity.PREFERENCES;

/**
 * Created by andrewsmith on 3/23/17.
 */

class SwitchCheckChangeListener implements OnCheckedChangeListener
{
    private String mChannel;
    private ProgressBar mSpinner;
    private TextView mStatus;
    private ConnectionManager mConnectionManager;
    private Context mContext;

    SwitchCheckChangeListener(Context context, ConnectionManager cm, ProgressBar pb, TextView tv, String c)
    {
        this.mContext = context;
        this.mConnectionManager = cm;
        this.mSpinner = pb;
        this.mStatus = tv;
        this.mChannel = c;
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked)
    {
        SharedPreferences settings = mContext.getSharedPreferences(PREFERENCES, 0);
        String mHost = settings.getString("host", "");
        String mUser = settings.getString("user", "");
        String mPass = settings.getString("pass", "");

        mSpinner.setVisibility(View.VISIBLE);
        String prefName = "ss" + mChannel;

        if (isChecked)
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(prefName, true);
            editor.apply();

            String command = "cd RelayControl/ && python relay_controller_android.py " + mChannel + " 0";
            mConnectionManager = new ConnectionManager(mHost, mUser, mPass, command, new ConnectionManager.AsyncResponse(){

                @Override
                public void processFinish(String output)
                {
                    if (output.contains("Unable to connect"))
                    {
                        buttonView.setChecked(false);
                        mStatus.setText(R.string.status_not_connected);
                        Toast toast = Toast.makeText(mContext, output, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        mStatus.setText(R.string.status_connected);
                    }
                    mSpinner.setVisibility(View.GONE);
                }
            });
            mConnectionManager.execute();
        }
        else
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(prefName, false);
            editor.apply();

            String command = "cd RelayControl/ && python relay_controller_android.py " + mChannel + " 1";
            mConnectionManager = new ConnectionManager(mHost, mUser, mPass, command, new ConnectionManager.AsyncResponse() {

                @Override
                public void processFinish(String output)
                {
                    if (output.contains("Unable to connect"))
                    {
                        buttonView.setChecked(false);
                        mStatus.setText(R.string.status_not_connected);
                        Toast toast = Toast.makeText(mContext, output, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        mStatus.setText(R.string.status_connected);
                    }
                    mSpinner.setVisibility(View.GONE);
                }
            });
            mConnectionManager.execute();
        }
    }
}
