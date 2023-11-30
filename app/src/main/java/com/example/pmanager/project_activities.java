package com.example.pmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class project_activities extends AppCompatActivity {

    TextView projectName;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_activities);
        getSupportActionBar().hide();

        Intent intent = getIntent();

        projectName = findViewById(R.id.projectNameOnActivitiesView);
        projectName.setText(intent.getStringExtra("projectName"));

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new Activities(), "ACTIVIDADES");
        vpAdapter.addFragment(new ProjectInformation(), "INFORMAÇÕES");
        viewPager.setAdapter(vpAdapter);
    }

    public String getProjectName() {
        Intent intent = getIntent();
        return intent.getStringExtra("projectName");
    }

    public String getProjectId() {
        Intent intent = getIntent();
        return intent.getStringExtra("projectId");
    }
}