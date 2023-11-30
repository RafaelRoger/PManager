package com.example.pmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pmanager.model.ActivityModel;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {

    private ArrayList<ActivityModel> activityDataset;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView activityNameTv;
        private final TextView activityStatusTv;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            activityNameTv = (TextView) view.findViewById(R.id.projectNameTv);
            activityStatusTv = (TextView) view.findViewById(R.id.projectStatusTv);
        }

    }

    public ActivityListAdapter(ArrayList<ActivityModel> departmentDataset) {
        this.activityDataset = departmentDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.activityNameTv.setText(activityDataset.get(position).getActivityName());
        viewHolder.activityStatusTv.setText("");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return activityDataset.size();
    }
}
