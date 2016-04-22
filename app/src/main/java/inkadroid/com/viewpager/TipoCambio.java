package inkadroid.com.viewpager;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class TipoCambio extends Fragment {

    //barra de progreso
    private ProgressDialog dialog;
    //control grafico
    private LineChart chart;
    public TipoCambio() {
        // Required empty public constructor
    }
    //orden de ejecucion 1
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tipo_cambio, container, false);
    }
    //orden de ejecucion 2
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //enlaces:
        //control grafico char
        chart = (LineChart) view.findViewById(R.id.chart);
        //control dialogo
        dialog = ProgressDialog.show(getContext(), getResources().getString(R.string.load_title),
                getResources().getString(R.string.load), true, false);
        ViewPager.getInstance().add(new StringRequest(Request.Method.GET,
                "http://www.inkadroid.com/usil2016/robot/2/bot.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i("Tipo de Cambio", response);
                        Type listType = new TypeToken<List<ExchangeType>>() {
                        }.getType();
                        List<ExchangeType> exchangeRate = new Gson().fromJson(response, listType);
                        setChart(exchangeRate);
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }));

    }
    private void setChart(List<ExchangeType> data) {
/*
        chart.setDescription("");
        chart.setNoDataTextDescription("nodoto");
        chart.setHighlightPerDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.TRANSPARENT);



        Legend l= chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        XAxis xl =chart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        YAxis yl =chart.getAxisLeft();
        yl.setTextColor(Color.WHITE);
        yl.setAxisMaxValue(4f);
        yl.setDrawGridLines(true);

        YAxis yl2=chart.getAxisRight();
        yl2.setEnabled(false);*/

        //profe
        ArrayList<Entry>valsCompra = new ArrayList<>();
        ArrayList<Entry>valsVenta = new ArrayList<>();
        ArrayList<String>valsX= new ArrayList<>();
        for(int i=0; i < data.size(); i++){
            valsCompra.add(new Entry(data.get(i).getCompra(),i));
            valsVenta.add(new Entry(data.get(i).getVenta(),i));
            valsX.add(String.valueOf(data.get(i).getDia()));
         }

        LineDataSet dataSetCompra= new LineDataSet(valsCompra, "Compra");
        dataSetCompra.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetCompra.setColor(getContext().getResources().getColor(R.color.colorPrimary));
        LineDataSet dataSetVenta = new LineDataSet(valsVenta,"Venta");
        dataSetVenta.setColor(getContext().getResources().getColor(R.color.colorAccent));
        dataSetVenta.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetCompra);
        dataSets.add(dataSetVenta);

        LineData lineData =new LineData(valsX,dataSets);
        chart.setData(lineData);
        chart.invalidate();

    }
}
