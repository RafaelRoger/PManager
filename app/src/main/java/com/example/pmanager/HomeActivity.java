package com.example.pmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmanager.model.ProjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView projectRv;
    ArrayList<ProjectModel> dataList = new ArrayList<>();
    ProjectListAdapter projectListAdapter;

    FirebaseFirestore db;
    String TAG = "Homepage query docs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_home);

        projectRv = findViewById(R.id.projectListRv);

        projectListAdapter = new ProjectListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        projectRv.setLayoutManager(layoutManager);
        projectRv.setAdapter(projectListAdapter);

        findViewById(R.id.addProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddProjectActivity.class));
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
                                dataList.add(projectModel);
                                projectListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });
    }
}
