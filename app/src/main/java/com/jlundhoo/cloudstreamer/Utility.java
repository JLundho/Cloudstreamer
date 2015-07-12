package com.jlundhoo.cloudstreamer;

import java.io.IOException;

/**
 * Created by jlundhol on 2015-07-12.
 */
public class Utility {
    /** Copied from http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts/27312494#27312494
     *  Pings the Google Servers, ensuring a 99.9999% chance that a connection error is on the user-end.
    */
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}


