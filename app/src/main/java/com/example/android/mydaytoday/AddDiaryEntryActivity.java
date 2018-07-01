package com.example.android.mydaytoday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDiaryEntryActivity extends AppCompatActivity {

    // An EditText variable for user input
    private EditText mEntryEditText;
    // A TextView object for displaying the present today's date
    private TextView mDateTextView;
    // A date object for storing the save time
    private Date now;
    // A formatted string for today's date
    private String todayString;
    // A database object
    private AppDatabase mDB;
    // A Toast object
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary_entry);

        mDateTextView = (TextView) findViewById(R.id.date_text_view);
        mEntryEditText = (EditText) findViewById(R.id.daily_entry_edit_text);
        // Instantiate the database object
        mDB = AppDatabase.getInstance(getApplicationContext());
        // Initialize the date string
        todayString = new SimpleDateFormat("dd MMMM, yyyy").format(new Date());
        mDateTextView.setText(todayString);
    }

    /**
     * Inflates the menu items*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_entry_menu, menu);
        return true;
    }

    /** Handles the menu items' clicks*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save){// If save was clicked
            if (TextUtils.isEmpty(mEntryEditText.getText())) {// If the EditText field is empty
                if (mToast != null) {// If there's a pending Toast
                    // Cancel the toast
                    mToast.cancel();
                }
                // Initialize the toast and show it
                mToast = Toast.makeText(getApplicationContext(),
                        getString(R.string.populate_field_prompt), Toast.LENGTH_SHORT);
                mToast.show();
            } else {// If the EditText field is not empty
                // Get the input and store it in activities
                String activities = mEntryEditText.getText().toString();
                // Initialize the date object getting the present time
                now = Calendar.getInstance().getTime();
                // Initialize a DiaryEntry object
                final DiaryEntry diaryEntry = new DiaryEntry(activities, todayString, now);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Insert the object in the database
                        mDB.diaryDao().insertDay(diaryEntry);
                        // Return to the previous activity
                        finish();
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
