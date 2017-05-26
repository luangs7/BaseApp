package br.com.luan2.baseapp.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.util.Log;

import com.github.rrsystems.utilsplus.android.UtilsPlus;
import com.google.firebase.messaging.FirebaseMessaging;

import java.net.InetAddress;


/**
 * Created by luan on 1/15/15.
 */
public class MyApplication extends Application {


    private static MyApplication sInstance;


    @Override
    public void onCreate() {
        //MultiDex.install(getApplicationContext());
        super.onCreate();
//        Realm.init(this);
        sInstance = this;
        UtilsPlus.initialize(getApplicationContext());

        try{
            FirebaseMessaging.getInstance().subscribeToTopic("general");
        }catch (Exception e){
            Log.e("teste",e.getMessage());
        }

    }

//    private static int VERSION_IMPORTACAO = 1;
//    public RealmConfiguration configurationSurvey(){
//
//        RealmConfiguration configuration = new RealmConfiguration.Builder()
//                .name("reymaster.realm")
//                .schemaVersion(1)
//                .modules(new ObjectModule())
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(configuration);
//        return configuration;
//    }



    /**
     * @return MyApplication singleton instance
     */
    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */







    public Context getContext() {
        return this;
    }


    /*public void downloadImage(Context context, String url, ImageView imageView) {
            Picasso.with(context).load(url).placeholder(R.drawable.img_placeholder_custom)
                    .error(R.drawable.img_placeholder_custom).into(imageView);
    }

    public void downloadImage(Context context, String url, ImageView imageView, Callback callback) {
        Picasso.with(context).load(url).placeholder(R.drawable.img_placeholder_custom)
                .error(R.drawable.img_placeholder_custom).into(imageView, callback);
    }*/


    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("https://www.google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

  /*  public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni == null ? false : true;
    } */

    public boolean isNetworkConnected() {
        try {
            ConnectivityManager nInfo = (ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            nInfo.getActiveNetworkInfo().isConnectedOrConnecting();

            ConnectivityManager cm = (ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean existeConexao(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo == null) {
                return false;
            }

            int netType = netInfo.getType();

            // Verifica se a conexão é do tipo WiFi ou Mobile e
            // retorna true se estiver conectado ou false em
            // caso contrário
            if (netType == ConnectivityManager.TYPE_WIFI ||
                    netType == ConnectivityManager.TYPE_MOBILE) {
                return netInfo.isConnected();

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void changeActionBarTitle(Activity activity, String title) {
        activity.getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + title + "</font>"));
    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }


    public String applicationName(){
        try{

            Resources appR = getContext().getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name","string", getContext().getPackageName()));
            return txt.toString();
        }catch (Exception ex){
            Log.e("applicationColor",ex.getMessage());
            return MyApplication.class.getPackage().getName().replace("br.com.","");
        }


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
