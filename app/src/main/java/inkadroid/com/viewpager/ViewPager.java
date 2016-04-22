package inkadroid.com.viewpager;
import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
/*
Esta es una clase de Aplicación utilizada para proporcionar objetos
compartidos para esta aplicación, tales como:Tracker
*/
public class ViewPager extends Application {
    private static final int TIME_OUT=10000;
    private static final int NUM_RETRY=3;
    private static final String TAG = Application.class.getName();
    private RequestQueue requestQueue;
    private static ViewPager instance;
    public static synchronized ViewPager getInstance(){
        return instance;
    }
    Tracker mTracker;

    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
        requestQueue= Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public <T> void add(Request<T> request){
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, NUM_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

    public void cancel(){
        requestQueue.cancelAll(TAG);
    }

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
