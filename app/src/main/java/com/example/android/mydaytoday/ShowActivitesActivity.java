/*
 * Copyright (C) 2018 Yusuff, Olawumi Qauzeem
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.mydaytoday;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;

public class ShowActivitesActivity extends AppCompatActivity {
    // An integer variable for storing the clicked entry's index
    private int entryIndex;
    // TextView fields for date and activities respectively
    private TextView dateTextView, activitiesTextView;
    // A database object
    private AppDatabase mDb;
    // A DiaryEntry object
    private DiaryEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activites);
        // Initialize the views
        dateTextView = findViewById(R.id.show_activities_date_text_view);
        activitiesTextView = findViewById(R.id.show_activities_activities_text_view);
        // Get the intent that started this activity
        Intent parentIntent = getIntent();

        if(parentIntent.hasExtra(Intent.EXTRA_INDEX)) {// If the intent has this extra
            // Store the extra as the entry's index
            entryIndex = parentIntent.getIntExtra(Intent.EXTRA_INDEX, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get the entry using the index
        entry = MainActivity.entries.get(entryIndex);
        // Set the date text using the creationDateString
        dateTextView.setText(entry.getCreationDateString());
        // Set the activities text
        activitiesTextView.setText(entry.getActivities());
        // Instantiate the database object
        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_activities_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:// If user clicks to edit entry
                // Start the edit entry activity
                Context context = getApplicationContext();
                Class destinationActivity = EditActivitiesActivity.class;
                Intent activityStarterIntent = new Intent(context, destinationActivity);
                activityStarterIntent.putExtra(Intent.EXTRA_INDEX, entryIndex);
                startActivity(activityStarterIntent);
                return true;
            case R.id.action_delete:// If user clicks to delete entry
                // Get entry's id
                int id = entry.getId();
                // Get entry's creation date string
                String creationDateString = entry.getCreationDateString();
                // Get entry's activities string
                String activities = entry.getActivities();
                // Get entry's update time
                Date updateAt = entry.getUpdatedAt();

                // Create a diary entry object using the above fields
                final DiaryEntry diaryEntry = new DiaryEntry(id, activities, creationDateString,
                        updateAt);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Delete the entry
                        mDb.diaryDao().deleteDay(diaryEntry);
                        finish();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
