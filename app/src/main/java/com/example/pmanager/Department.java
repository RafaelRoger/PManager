package com.example.pmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pmanager.model.DepartmentModel;
import com.example.pmanager.model.ProjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Department extends Fragment implements View.OnClickListener {

    RecyclerView departmentRv;
    ArrayList<DepartmentModel> dataList = new ArrayList<>();
    DepartmentListAdapter departmentListAdapter;
    FirebaseFirestore db;
    String TAG = "Homepage query docs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department, container, false);

        departmentRv = view.findViewById(R.id.departmentListRv);

        departmentListAdapter = new DepartmentListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        departmentRv.setLayoutManager(layoutManager);
        departmentRv.setAdapter(departmentListAdapter);

        view.findViewById(R.id.addDepartment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddDepartmentActivity.class));
            }
        });

        db = FirebaseFirestore.getInstance();

        db.collection("departments")
                //.whereEqualTo("try", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                DepartmentModel departmentModel = document.toObject(DepartmentModel.class);
                                dataList.add(departmentModel);
                                departmentListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });


        return  view;
    }

    @Override
    public void onClick(View view) {

    }
}