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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // The RecyclerView member variable
    RecyclerView mRecyclerView;
    // The Adapter member variable
    private RecyclerView.Adapter mAdapter;
    // The LayoutManager member variable
    private RecyclerView.LayoutManager mLayoutManager;
    // A Date variable for storing the update time of an entry
    private Date now;
    // A List variable for storing callbacks for the entries in the database
    public static List<DiaryEntry> entries = new ArrayList<>();
    // An AppDatabase object
    private AppDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind mRecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_diary);
        // Bind the FloatingActionButton on the main UI
        FloatingActionButton fabAddItem = (FloatingActionButton) findViewById(R.id.fab_add_item);
        // Bind the LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        // Fix the size of the recycler view layout
        mRecyclerView.setHasFixedSize(true);
        // Set LayoutManager on the on the recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Instantiate the database object.
        mDB = AppDatabase.getInstance(getApplicationContext());

        // Initialize an bind the floating action button in the layout
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddDiaryItemActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Load all entries in the database
                entries = mDB.diaryDao().loadAllDays();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!entries.isEmpty()) {// If entries is not empty
                            // make the recycler view visible
                            mRecyclerView.setVisibility(View.VISIBLE);
                            // Create a new adapter and configure its item click listener
                            mAdapter = new DiaryAdapter(entries, new DiaryAdapter.OnListItemClickListener() {
                                @Override
                                public void onListItemClick(int index) {
                                    // Store the application context
                                    Context context = MainActivity.this;
                                    // The destination activity
                                    Class destinationActivity = ShowActivitesActivity.class;
                                    // Create a new intent
                                    Intent showActivitiesIntent = new Intent(context, destinationActivity);
                                    // Put the item index as an extra in the intent
                                    showActivitiesIntent.putExtra(Intent.EXTRA_INDEX, index);
                                    // Start the activity
                                    startActivity(showActivitiesIntent);
                                }
                            });
                            // Set adapter on the recycler view
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            // If no entry
                            // Make the recycler view gone
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    /** Starts the activity for adding a new diary item
     * */
    public void startAddDiaryItemActivity() {
        Context context = MainActivity.this;
        Class destinationActivity = AddDiaryEntryActivity.class;
        Intent addItemActivityIntent = new Intent(context, destinationActivity);
        startActivity(addItemActivityIntent);
    }
}
