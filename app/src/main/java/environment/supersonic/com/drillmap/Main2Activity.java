package environment.supersonic.com.drillmap;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import environment.supersonic.com.drillmap.model.DatabaseClient;
import environment.supersonic.com.drillmap.model.Distance;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerview = findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getTasks();
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Distance>> {

            @Override
            protected List<Distance> doInBackground(Void... voids) {
                List<Distance> distanceList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .distanceDao()
                        .getAll();
                return distanceList;
            }

            @Override
            protected void onPostExecute(List<Distance> distances) {
                super.onPostExecute(distances);

                Log.e("distances",distances.toString());

                DistanceAdapter adapter = new DistanceAdapter(Main2Activity.this,distances);
                recyclerview.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
