package inkadroid.com.viewpager;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
//adapatador
public class Adapter extends PagerAdapter {

    private Activity activity;

    public Adapter(Activity activity){
    this.activity = activity;
    }

    public Object instantiateItem(ViewGroup collection, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.pagina1;
                break;
            case 1:
                resId = R.id.pagina2;
                break;
            case 2:
                resId = R.id.pagina3;
                break;
            case 3:
                resId = R.id.pagina4;
                break;
            case 4:
                resId = R.id.pagina5;
                break;
            case 5:
                resId = R.id.HomeLayout;
                break;
        }
        return activity.findViewById(resId);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == ((View) o);    }
}
