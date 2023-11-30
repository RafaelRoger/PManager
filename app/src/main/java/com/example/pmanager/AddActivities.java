package com.example.pmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pmanager.model.ActivityModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddActivities extends AppCompatActivity {

    EditText etActivityInput;
    AppCompatButton saveBtn;

    FirebaseFirestore db;
    String TAG = "ActivityList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBtn = findViewById(R.id.activitySaveBtn);
        etActivityInput = findViewById(R.id.inputActivityName);

        db = FirebaseFirestore.getInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = etActivityInput.getText().toString().trim();
                if (activityName != null) {
                    Intent intent = getIntent();
                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    ActivityModel activityModel = new ActivityModel("", intent.getStringExtra("projectId"),activityName, "PENDING");
                    db.collection("activities").add(activityModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with id: " + documentReference.getId());

                            findViewById(R.id.successLayout).setVisibility(View.VISIBLE);
                            findViewById(R.id.progress).setVisibility(View.GONE);
                            findViewById(R.id.addProjectLayout).setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Error adding document", e);
                        }
                    });
                }
            }
        });
    }
}