package inkadroid.com.viewpager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {
    private ViewPager guide_pager;
    private ImageView imageView;
    private ImageButton ir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /////////////google analytic///////////////////////////////////////////////////
        Tracker t = ((inkadroid.com.viewpager.ViewPager) getApplication()).getTracker();
        t.setScreenName("Pagina - Principal");
        t.send(new HitBuilders.AppViewBuilder().build());
        /////////////google analytic///////////////////////////////////////////////////

        ////////////detectar la resolucion de densidad de la pantalla//////////////////
        Resolucion();
        ////////////detectar la resolucion de densidad de la pantalla//////////////////

        ////////////adpatador del ViewPager//////////////////
        Adapter adapter = new Adapter(this);
        guide_pager = (ViewPager)findViewById(R.id.guide_pager);
        guide_pager.setAdapter(adapter);
        ////////////adpatador del ViewPager//////////////////

        //////////NUMERO DE PAGINAS RETENIDAS AMBOS LADOS/////////
        guide_pager.setOffscreenPageLimit(6);
        guide_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            ///////////AL SELECCIONAR UNA NUEVA PAGINA/////////
            @Override
            public void onPageSelected(int i) {

                if (i == 5) {
                    guide_pager.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //IMAGEN PRINCIPAL DE LA PAGINA 6
        imageView = (ImageView)findViewById(R.id.ImagenPrincipal);
        //BOTON REDONDEADO DE LA PAGINA PRINCIPAL
        ir = (ImageButton)findViewById(R.id.ir);
        //AL PULSAR EL BOTON REDONDEADO DE LA PAGINA PRINCIPAL
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent downloadIntent = new Intent(MainActivity.this, Principal.class);
                startActivity(downloadIntent);
            }
        });

        //COLOCAR EN LA PAGINA PRINCPAL UNA IMAGEN CARGADA A TRAVEZ DE Picasso
        Picasso.with(this).load(Constantes.URL_IMAGEN).into(imageView);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean first_time = sp.getBoolean("first time", true);

        if (!first_time) {
            /////////////google analytic///////////////////////////////////////////////////
            t.setScreenName("Pagina - Principal");
            t.send(new HitBuilders.AppViewBuilder().build());
            /////////////google analytic///////////////////////////////////////////////////
            guide_pager.setCurrentItem(5);

        }else{
            sp.edit().putBoolean("first time",false).apply();
        }
    }

    //obteniendo la resolucion de la pantalla
    private void Resolucion()
    {
        Resources resources = getResources();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch(metrics.densityDpi)
        {
            case DisplayMetrics.DENSITY_XXXHIGH: //HDPI
                Toast.makeText(getApplication(),"EXTRA EXTRA EXTRA ALTA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
            case DisplayMetrics.DENSITY_XXHIGH: //HDPI
                Toast.makeText(getApplication(),"EXTRA EXTRA ALTA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
            case DisplayMetrics.DENSITY_XHIGH: //HDPI
                Toast.makeText(getApplication(),"EXTRA ALTA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
            case DisplayMetrics.DENSITY_HIGH: //HDPI
                Toast.makeText(getApplication(),"ALTA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI
                Toast.makeText(getApplication(),"MEDIANA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
            case DisplayMetrics.DENSITY_LOW:  //LDPI
                Toast.makeText(getApplication(),"BAJA DENSIDAD",Toast.LENGTH_LONG).show();
                break;
        }
    }


}