package com.example.pmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.pmanager.model.DepartmentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDepartmentActivity extends AppCompatActivity {
    EditText etDepartmentInput;
    AppCompatButton saveBtn;

    FirebaseFirestore db;
    String TAG = "DepartmentList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBtn = findViewById(R.id.departmentSaveBtn);
        etDepartmentInput = findViewById(R.id.inputDepartmentName);

        db = FirebaseFirestore.getInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String departmentName = etDepartmentInput.getText().toString().trim();
                if (departmentName != null) {
                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    DepartmentModel departmentModel = new DepartmentModel("", departmentName);
                    db.collection("departments").add(departmentModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
