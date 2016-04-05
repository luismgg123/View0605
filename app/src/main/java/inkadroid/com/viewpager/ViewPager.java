package inkadroid.com.viewpager;
import android.app.Application;
import android.content.SharedPreferences;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
/*
Esta es una clase de Aplicación utilizada para proporcionar objetos
compartidos para esta aplicación, tales como:Tracker
*/
public class ViewPager extends Application {
    Tracker mTracker;


    public synchronized Tracker getTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            //registro de depuracion de eventos sobre nuestra Apps
            Tracker t = analytics.newTracker("UA-75898553-1");

            mTracker = t;

        }
        return mTracker;
    }
}
