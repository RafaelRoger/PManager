package com.example.pmanager;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmanager.model.ProjectModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private ArrayList<ProjectModel> projectDataset;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectNameTv;
        private final TextView projectStatusTv;

        LinearLayout container;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            projectNameTv = (TextView) view.findViewById(R.id.projectNameTv);
            projectStatusTv = (TextView) view.findViewById(R.id.projectStatusTv);
            container = (LinearLayout) view.findViewById(R.id.container);
        }

    }

    public ProjectListAdapter(ArrayList<ProjectModel> projectDataset) {
        this.projectDataset = projectDataset;
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

        viewHolder.projectNameTv.setText(projectDataset.get(position).getProjectName());
        viewHolder.projectStatusTv.setText(projectDataset.get(position).getProjectStatus());

        String status = projectDataset.get(position).getProjectStatus();

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), project_activities.class);

                intent.putExtra("projectId", projectDataset.get(position).getProjectId());
                intent.putExtra("projectName", projectDataset.get(position).getProjectName());

                view.getContext().startActivity(intent);
            }
        });

        viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), viewHolder.container);
                popupMenu.inflate(R.menu.project_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.deleteMenu) {
                            FirebaseFirestore.getInstance().collection("projects").document(projectDataset.get(position).getProjectId()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(view.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                                            viewHolder.container.setVisibility(view.GONE);
                                        }
                                    });
                        }
                        if (menuItem.getItemId() == R.id.markCompleteMenu) {
                            ProjectModel completedProject = projectDataset.get(position);
                            completedProject.setProjectStatus("completed");
                            FirebaseFirestore.getInstance().collection("projects").document(projectDataset.get(position).getProjectId())
                                    .set(completedProject).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(view.getContext(), "Item Marked As Completed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        return false;
                    }
                });
                return false;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return projectDataset.size();
    }


}
