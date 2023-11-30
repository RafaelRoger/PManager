package com.example.pmanager;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pmanager.model.ProjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Projects extends Fragment implements View.OnClickListener {
    RecyclerView projectRv;
    ArrayList<ProjectModel> dataList = new ArrayList<>();
    ProjectListAdapter projectListAdapter;
    FirebaseFirestore db;
    String TAG = "Homepage query docs";
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actitvity_home, container, false);

        projectRv = view.findViewById(R.id.projectListRv);

        projectListAdapter = new ProjectListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        projectRv.setLayoutManager(layoutManager);
        projectRv.setAdapter(projectListAdapter);

        view.findViewById(R.id.addProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddProjectActivity.class));
            }
        });

        db = FirebaseFirestore.getInstance();

        db.collection("projects")
                //.whereEqualTo("try", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                ProjectModel projectModel = document.toObject(ProjectModel.class);
                                projectModel.setProjectId(document.getId());
                                dataList.add(projectModel);
                                projectListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                Toast.makeText(getContext(), newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return  view;
    }

    private void filterList(String text) {
        db.collection("projects")
                .whereEqualTo("projectName", text)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                ProjectModel projectModel = document.toObject(ProjectModel.class);
                                projectModel.setProjectId(document.getId());
                                dataList.add(projectModel);
                                projectListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }
}