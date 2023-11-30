package com.example.pmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pmanager.model.DepartmentModel;

import java.util.ArrayList;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.ViewHolder> {
    private ArrayList<DepartmentModel> departmentDataset;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectNameTv;
        private final TextView projectStatusTv;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            projectNameTv = (TextView) view.findViewById(R.id.projectNameTv);
            projectStatusTv = (TextView) view.findViewById(R.id.projectStatusTv);
        }

    }

    public DepartmentListAdapter(ArrayList<DepartmentModel> departmentDataset) {
        this.departmentDataset = departmentDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DepartmentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DepartmentListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.projectNameTv.setText(departmentDataset.get(position).getDepartmentName());
        viewHolder.projectStatusTv.setText("");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return departmentDataset.size();
    }
}
