package com.chrynan.puzzle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.chrynan.puzzle.model.Project;

import java.util.ArrayList;

/**
 * Created by chRyNaN on 12/18/2015. An Activity which allows the user to choose projects and returns the result to the calling
 * Activity. Also, provides another option for other applications to query projects without having to use the Content Provider.
 */
public class ProjectPickerActivity extends AppCompatActivity {
    public static final String PROJECTS_SELECTED = "com.chrynan.puzzle.PROJECTS_SELECTED";
    public static final String PROJECTS_EXTRA = "com.chrynan.puzzle.PROJECTS_EXTRA";

    private String title;
    private boolean multiselect = false;
    private boolean mustSelect = false;

    private Toolbar toolbar;
    private ListView listView;
    private ProjectAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ProjectAdapter(this);
        adapter.loadProjects();
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        multiselect = intent.getBooleanExtra("multiselect", false);
        adapter.setMultiselect(multiselect);
        mustSelect = intent.getBooleanExtra("mustSelect", false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project, menu);
        menu.findItem(R.id.action_done).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(title != null){
                actionBar.setTitle(title);
            }else {
                actionBar.setTitle("Choose Projects");
            }
        }
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                try {
                    //ghost error occurring when pressing the up button on the nav bar
                    //causes the app to close but not crash
                    Intent intent = NavUtils.getParentActivityIntent(this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    setResult(RESULT_CANCELED);
                    NavUtils.navigateUpTo(this, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    onBackPressed();
                }
                return true;
            case R.id.action_done:
                ArrayList<Project> selectedProjects = (ArrayList<Project>) adapter.getSelectedProjects();
                if(mustSelect && (selectedProjects == null || selectedProjects.size() < 1)){
                    Toast.makeText(this, "You must select a project to continue.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.setAction(PROJECTS_SELECTED);
                    Bundle b = new Bundle();
                    b.putSerializable(PROJECTS_EXTRA, selectedProjects);
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
