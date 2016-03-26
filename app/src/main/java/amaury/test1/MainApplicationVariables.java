package amaury.test1;

import android.app.Application;

/**
 * Created by Amaury Punel on 25/03/2016.
 */

public class MainApplicationVariables extends Application {

    private static String username;
    private static String password;
    private static int userID;

    @Override
    public void onCreate() {
        super.onCreate();
        username="";
        password="";
        userID=0;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        MainApplicationVariables.userID = userID;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MainApplicationVariables.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MainApplicationVariables.password = password;
    }


}