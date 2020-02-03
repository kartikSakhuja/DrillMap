package environment.supersonic.com.drillmap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import environment.supersonic.com.drillmap.model.DatabaseClient;
import environment.supersonic.com.drillmap.model.Distance;
import environment.supersonic.com.drillmap.model.DrawView;
import environment.supersonic.com.drillmap.model.KruskalAlgorithm;

public class MainActivity extends MapsActivity implements GoogleMap.OnMarkerDragListener,TextToSpeech.OnInitListener {

    private TextView mTextView, mTextView1;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;
    private Polyline mPolyline, mPolyline1, mPolyline2, mPolyline3;
    private ArrayList<Double> distanceList = new ArrayList<>();
    private double shortest;
    private Button btn_view;
    TextToSpeech t1;

    public static int number=0;
    public static int numberOfNode=-5;
    public static float firsX ;
    public static float firstY;
    public static String cost="0";
    public final KruskalAlgorithm kruskalobject = new KruskalAlgorithm();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void startDemo() {

        mTextView = findViewById(R.id.textView);
        mTextView1 = findViewById(R.id.textView1);

        t1 = new TextToSpeech(this,this);


        btn_view = findViewById(R.id.btn_view);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8256, 151.2395), 10));
        getMap().setOnMarkerDragListener(this);

        mMarkerA = getMap().addMarker(new MarkerOptions().position(new LatLng(-33.9046, 151.155)).draggable(true));
        mMarkerB = getMap().addMarker(new MarkerOptions().position(new LatLng(-33.8291, 151.248)).draggable(true));
        mMarkerC = getMap().addMarker(new MarkerOptions().position(new LatLng(-31.9046, 151.155)).draggable(true));
        mMarkerD = getMap().addMarker(new MarkerOptions().position(new LatLng(-31.8291, 151.248)).draggable(true));
        mPolyline = getMap().addPolyline(new PolylineOptions().geodesic(true));
        mPolyline1 = getMap().addPolyline(new PolylineOptions().geodesic(true));
        mPolyline2 = getMap().addPolyline(new PolylineOptions().geodesic(true));
        mPolyline3 = getMap().addPolyline(new PolylineOptions().geodesic(true));

        Toast.makeText(this, "Drag the markers!", Toast.LENGTH_LONG).show();
        showDistance();
//        createCircle();
    }

    private void showDistance() {

        double distance = SphericalUtil.computeDistanceBetween(mMarkerA.getPosition(), mMarkerB.getPosition());
        double distance1 = SphericalUtil.computeDistanceBetween(mMarkerC.getPosition(), mMarkerD.getPosition());
        double distance2 = SphericalUtil.computeDistanceBetween(mMarkerA.getPosition(), mMarkerC.getPosition());


        mTextView.setText("The markers A and B are " + formatNumber(distance) + " apart.");

        mTextView1.setText("The markers C and D are " + formatNumber(distance1) + " apart.");

        // As a straight line is a shortest path connecting 4 points.

        shortest = distance+distance1+distance2;

        distanceList.add(shortest);
        Log.e("Shortest", String.valueOf(shortest));


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    private void updatePolyline() {
        mPolyline.setPoints(Arrays.asList(mMarkerA.getPosition(), mMarkerB.getPosition()));
        mPolyline1.setPoints(Arrays.asList(mMarkerC.getPosition(), mMarkerD.getPosition()));
        mPolyline2.setPoints(Arrays.asList(mMarkerA.getPosition(), mMarkerC.getPosition()));
    }

    private String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }

        return String.format("%4.3f%s", distance, unit);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        showDistance();
        updatePolyline();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        showDistance();
        updatePolyline();
        saveTask();
        speakOut();
    }
    private void saveTask() {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Distance distance = new Distance();
                distance.setDistance(shortest);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .distanceDao()
                        .insert(distance);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = t1.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            }

        } else {

            Toast.makeText(getBaseContext(), "TTS execution Failed!", Toast.LENGTH_SHORT).show();
        }
    }
    private void speakOut() {
        t1.speak(String.valueOf(shortest)+"metres",TextToSpeech.QUEUE_FLUSH, null);
    }

}

