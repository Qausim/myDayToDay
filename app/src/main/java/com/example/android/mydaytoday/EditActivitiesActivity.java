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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class EditActivitiesActivity extends AppCompatActivity {
    // A database object
    private AppDatabase mDb;
    // Integers variables for storing the id and entry index
    private int id, entryIndex;
    // String variable for storing the entry's creation date
    private String creationDateString;
    // For the activities
    private String activities;
    // For the update time
    private Date updateAt;
    // A TextView object that displays the date
    private TextView dateTextView;
    // An EditText object for user input
    private EditText activitiesEditText;
    // A toast object
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activities);

        // Initialize the views
        dateTextView = findViewById(R.id.edit_activities_date_text_view);
        activitiesEditText = findViewById(R.id.edit_activities_activities_edit_text);
        // Instantiate the database
        mDb = AppDatabase.getInstance(getApplicationContext());

        // Get the intent that started this activity and get its extra which is the clicked entry's
        // index if actually the extra does exist
        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra(Intent.EXTRA_INDEX)) {
            entryIndex = parentIntent.getIntExtra(Intent.EXTRA_INDEX, 0);
            // Use the index to get the entry
            DiaryEntry entry = MainActivity.entries.get(entryIndex);
            // Get the id of the entry
            id = entry.getId();
            // Get the activities of the entry
            activities = entry.getActivities();
            // Get the creationString of the entry
            creationDateString = entry.getCreationDateString();

            // Set the text of the date text view to the creationString
            dateTextView.setText(creationDateString);
            // Set text of the edit text to the activities
            activitiesEditText.setText(activities);
        }
    }

    /** Inflates the menu items*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_entry_menu, menu);
        return true;
    }

    /** Handles the menu items' clicks*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {// If save was clicked
            if (TextUtils.isEmpty(activitiesEditText.getText())) {// If the edit text field is empty
                if (toast != null) {// if there's a pending toast
                    toast.cancel();
                }
                // Prompt user to populate field
                toast = Toast.makeText(getApplicationContext(), getString(R.string
                                .populate_field_prompt), Toast.LENGTH_SHORT);
                toast.show();
            } else {// If edit text field is not empty
                // Store the input string
                activities = activitiesEditText.getText().toString();
                // Store the update date
                updateAt = new Date();
                // Create a diary entry object using
                final DiaryEntry diaryEntry = new DiaryEntry(id, activities,creationDateString, updateAt);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Update the entry
                        mDb.diaryDao().updateDay(diaryEntry);
                        MainActivity.entries = mDb.diaryDao().loadAllDays();
                        finish();
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
