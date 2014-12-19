package com.star.dynamicachart;

import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

/**
 * @author star
 * Dynamic update line chart forward
 */
public class MainActivity extends Activity {

	private Timer timer = new Timer();  
    private TimerTask task;  
    private Handler handler;  
    
    private String title = "Signal Strength";  
    private XYSeries series;  
    private XYMultipleSeriesDataset mDataset;  
    private GraphicalView chart;  
    private XYMultipleSeriesRenderer renderer;  
    private Context context;  
    private int addX = -1, addY;  
      
    int[] xv = new int[100];  
    int[] yv = new int[100];  
      
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
          
        context = getApplicationContext();  
          
        //1.view container
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);  
          
        //2.prepare series 
        series = new XYSeries(title);  
          
        // A series that includes 0 to many XYSeries. 
        mDataset = new XYMultipleSeriesDataset();  
          
        //  
        mDataset.addSeries(series);  
          
        //3.prepare renderer for  series
        int color = Color.BLACK;  
        PointStyle style = PointStyle.CIRCLE;  
        renderer = buildRenderer(color, style, true);  
          
        //4.draw series with renderer  
        setChartSettings(renderer, "X", "Y", 0, 100, 0, 90, Color.WHITE, Color.WHITE);  
          
        //5.create chart view  
        chart = ChartFactory.getLineChartView(context, mDataset, renderer);  
          
        //6.add view to container  
        layout.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
          
        //Handler Timer  
        handler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                //refresh chart at UI thread   
                updateChart();  
                super.handleMessage(msg);  
            }  
        };  
          
        task = new TimerTask() {  
            @Override  
            public void run() {  
                Message message = new Message();  
                message.what = 1;  
                handler.sendMessage(message);  
            }  
        };  
          
        timer.schedule(task, 500, 500);  
          
    }  
      
    @Override  
    public void onDestroy() {  
        //Timer  
        timer.cancel();  
        super.onDestroy();  
    }  
    
    /**
     * 
     * @param color color for chart line
     * @param style style for value point
     * @param fill whether fill the value point
     * @return
     */
    protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style, boolean fill) {  
    	//Multiple XY series renderer.
    	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();  
          
        //A renderer for the XY type series.  
        XYSeriesRenderer r = new XYSeriesRenderer();  
        r.setColor(color);  
        r.setPointStyle(style);  
        r.setFillPoints(fill);  
        r.setLineWidth(3);  
        renderer.addSeriesRenderer(r);  
          
        return renderer;  
    }  
    
    /**
     * 
     * @param renderer
     * @param xTitle the title of X Axis
     * @param yTitle the title of Y Axis
     * @param xMin the minimum value of X Axis
     * @param xMax the maximum value of X Axis
     * @param yMin
     * @param yMax
     * @param axesColor the color of axes
     * @param labelsColor  
     */
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String xTitle, String yTitle,  
                                    double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {  
        //api  
        renderer.setChartTitle(title);  
        renderer.setXTitle(xTitle);  
        renderer.setYTitle(yTitle);  
        renderer.setXAxisMin(xMin);  
        renderer.setXAxisMax(xMax);  
        renderer.setYAxisMin(yMin);  
        renderer.setYAxisMax(yMax);  
        renderer.setAxesColor(axesColor);  
        renderer.setLabelsColor(labelsColor);  
        renderer.setShowGrid(true);  
        renderer.setGridColor(Color.GREEN);  
        renderer.setXLabels(20);  
        renderer.setYLabels(10);  
        renderer.setXTitle("Time");  
        renderer.setYTitle("dBm");  
        renderer.setYLabelsAlign(Align.RIGHT);  
        renderer.setPointSize((float) 2);  
        renderer.setShowLegend(false);  
    }  
      
    /**
     * Dynamic update line chart forward
     * Data structure of series like ArrayList<HashhMap<double,double>>
     * Data structure of mDataset like ArrayList<ArrayList<HashMap<double,double>>>
     * Algorithm: always save the latest 100 points to series
     */
    private void updateChart() {  
          
        // new point  
        addX = 0;  
        addY = (int)(Math.random() * 90);  
          
        //remove series from dataset  
        mDataset.removeSeries(series);  
          
        //limit the count of points 
        int length = series.getItemCount();  
        if (length > 100) {  
            length = 100;  
        }  
          
        //get the latest values    
        for (int i = 0; i < length; i++) {  
            xv[i] = (int) series.getX(i) + 1;  
            yv[i] = (int) series.getY(i);  
        }  
          
        //you must clear the series first 
        //then add new value of point or
        //you'll get the overlayed points
        series.clear();  
          
        //create new series   
        series.add(addX, addY);  
        for (int k = 0; k < length; k++) {  
            series.add(xv[k], yv[k]);  
        }  
          
        //add series to dataset  
        mDataset.addSeries(series);  
          
        //redraw the view
        //postInvalidate() 
        chart.invalidate();  
    }  
}
