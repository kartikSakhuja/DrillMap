package environment.supersonic.com.drillmap;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;

import java.util.List;

import environment.supersonic.com.drillmap.model.Distance;

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Distance> distanceList;

    public DistanceAdapter(Context mCtx, List<Distance> distanceList) {
        this.mCtx = mCtx;
        this.distanceList = distanceList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_row_distance, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Distance distance = distanceList.get(position);
        holder.tv_distance.setText(String.valueOf(distance.getDistance())+"m");
    }

    @Override
    public int getItemCount() {
        return distanceList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder  {

        TextView tv_distance;

        public TasksViewHolder(View itemView) {
            super(itemView);

            tv_distance = itemView.findViewById(R.id.tv_distance);

        }

    }
}