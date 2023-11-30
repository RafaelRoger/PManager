package com.example.pmanager;

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

import com.example.pmanager.model.ActivityModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Activities extends Fragment {

    RecyclerView activityRv;
    ArrayList<ActivityModel> dataList = new ArrayList<>();
    ActivityListAdapter activityListAdapter;
    FirebaseFirestore db;
    String TAG = "Homepage query docs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        activityRv = view.findViewById(R.id.activitiesListRv);

        activityListAdapter = new ActivityListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        activityRv.setLayoutManager(layoutManager);
        activityRv.setAdapter(activityListAdapter);

        Intent intent = new Intent(view.getContext(), AddActivities.class);
        project_activities activity = (project_activities) getActivity();

        view.findViewById(R.id.addActivities).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("projectId", activity.getProjectId());
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();

        db.collection("activities")
                .whereEqualTo("projectId", activity.getProjectId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                ActivityModel activityModel = document.toObject(ActivityModel.class);
                                activityModel.setActivityId(document.getId());
                                dataList.add(activityModel);
                                activityListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });



        return view;
    }
}