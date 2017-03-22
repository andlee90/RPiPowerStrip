package com.example.rpipowerstrip;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * Created by andrewsmith on 3/19/17.
 */

class ConnectionManager extends AsyncTask<String, Void, String>
{
    private String mHost;
    private String mUser;
    private String mPass;
    private String mCommand;
    private AsyncResponse mDelegate = null;

    ConnectionManager(String h, String u, String p, String cmd, AsyncResponse delegate)
    {
        this.mHost = h;
        this.mUser = u;
        this.mPass = p;
        this.mCommand = cmd;
        this.mDelegate = delegate;
    }

    interface AsyncResponse
    {
        void processFinish(String output);
    }

    @Override
    protected String doInBackground(String... strings)
    {
        String status = "Unable to connect to " + mUser + "@" + mHost;

        Properties props = new Properties();
        props.put("StrictHostKeyChecking", "no");
        props.put("cipher.s2c", "aes128-ctr,aes128-cbc,3des-ctr," +
                "3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc," +
                "aes256-ctr,aes256-cbc");

        try
        {
            JSch jsch = new JSch();

            Session session = jsch.getSession(mUser, mHost, 22);
            session.setConfig(props);
            session.setPassword(mPass);
            session.connect();

            status = "Client: " + session.getClientVersion() +
                    "\nServer: " + session.getServerVersion() +
                    "\nUser/Host: " + mUser + "@" + mHost +
                    "\nCommand: " + mCommand;

            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand(mCommand);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            channel.connect();

            channel.disconnect();
            session.disconnect();
        }

        catch (JSchException e)
        {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    protected void onPostExecute(String status)
    {
        mDelegate.processFinish(status);
    }
}