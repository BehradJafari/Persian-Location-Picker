package ir.hamiss.persianaddresspicker;


import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by behrad on 9/6/2017.
 */
public class G extends Application  {

    //permissions

    public static String[] perms = {
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION};


    public static Context context;

    public static LayoutInflater inflater;

    private static SharedPreferences sharedPref;
    private static String token;
    private static String name;
    private static String type;
    private static String school;
    private static String username;
    public static Typeface tf;

    public static final int LESSON = 346;
    public static final int VOTE = 346;
    public static final int MESSAGE = 346;





    @Override
    public void onCreate() {

        super.onCreate();





        //region statics
        context = getApplicationContext();
        inflater = (LayoutInflater) getSystemService(context.LAYOUT_INFLATER_SERVICE);



        //endregion

        //region locality
//        Locale locale = new Locale("en");
//        Locale.setDefault(locale);
//        Configuration config = getApplicationContext().getResources().getConfiguration();
//
//        config.setLocale(locale);
//        getApplicationContext().createConfigurationContext(config);
        //endregion







    }


    public static String getToken() {

        getFromPref();

        return "Bearer " + token;
    }

    public static String token() {

        getFromPref();
        return token;
    }

    public static String getName() {
        getFromPref();
        return name;
    }

    public static String getType() {
        getFromPref();
        return type;
    }

    public static String getSchool() {
        getFromPref();
        return school;
    }

    public static String getUsername() {
        getFromPref();
        return username;
    }


    public static void getFromPref() {

        sharedPref = context.getSharedPreferences("Hamiss", context.MODE_PRIVATE);

        token = sharedPref.getString("TOKEN", null);
        name = sharedPref.getString("NAME", null);
        type = sharedPref.getString("TYPE", null);
        school = sharedPref.getString("SCHOOL", null);
        username = sharedPref.getString("USERNAME", null);


    }

    public static void addToSharedPref(String token, String username, String name) {

        sharedPref = context.getSharedPreferences("Hamiss", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN", token);
        editor.putString("NAME", name);
        editor.putString("TYPE", "c");

        editor.putString("USERNAME", username);

        editor.apply();
    }

    public static void logout() {

        sharedPref = context.getSharedPreferences("Hamiss", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN", null);
        editor.putString("NAME", null);
        editor.putString("TYPE", null);
        editor.putString("SCHOOL", null);
        editor.putString("USERNAME", null);

        editor.apply();
    }

    public static boolean first_time() {
        sharedPref = context.getSharedPreferences("Hamiss", Context.MODE_PRIVATE);


        boolean r = true;

        String first = sharedPref.getString("FIRST", null);


        if (first != null) {
            r = false;
        }


        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("FIRST", "NO");

        editor.apply();


        return r;

    }


    public SharedPreferences getSharedPref() {
        return sharedPref;
    }






    public static int screenWidth(int mul, int dev) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (size.x * mul) / dev;


    }

    public static int screenHeight(int mul, int dev) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        return (size.y * mul) / dev;


    }

    public static String getAuth() {

        return "Application/json";
    }






    public static void toast(String message) {
//        TextView mTextView =  mView.findViewById(R.id.snackbar_text);

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

//        Snackbar.make( "Click the pin for more options", Snackbar.LENGTH_LONG).show();

    }



    public static int build_num() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;

            return verCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }







}
