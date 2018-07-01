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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 6/27/2018.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    // Stores a list of diary entries
    private List<DiaryEntry> entries = new ArrayList<>();
    // An listener object
    OnListItemClickListener mOnListItemClickListener;

    /**Constructor of the DiaryAdapter class
     * @param entries
     * @param listItemClickListener*/
    public DiaryAdapter(List<DiaryEntry> entries, OnListItemClickListener listItemClickListener) {
        this.entries = entries;
        mOnListItemClickListener = listItemClickListener;
    }

    // The view holder class
    class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView fields for each in the layout
        TextView dateTextView, updateDateTextView, updateTimeTextView;

        /**Constructor of the DiaryViewHolder class*/
        public DiaryViewHolder(View itemView) {
            super(itemView);
            // Bind each view
            dateTextView = itemView.findViewById(R.id.tv_date);
            updateDateTextView = (TextView) itemView.findViewById(R.id.tv_update_date);
            updateTimeTextView = (TextView) itemView.findViewById(R.id.tv_update_time);
            // set click listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnListItemClickListener.onListItemClick(clickedPosition);
        }
    }

    /** Creates a view holder object
     * @return the object*/
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        // store the item layout id
        int diaryItemLayoutId = R.layout.diary_item_layout;
        // Initialize an inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        // attach to parent immediately or not
        boolean attachToParentImmediately = false;
        // Inflate the layout and return a view holder object
        View view = inflater.inflate(diaryItemLayoutId, parent, attachToParentImmediately);
        return new DiaryViewHolder(view);
    }

    /** Binds data to each view*/
    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        // Initialize a variable that stores the present entry
        DiaryEntry entry = entries.get(position);
        // set the date string
        holder.dateTextView.setText(entry.getCreationDateString());
        // Get the time of update
        Date date = entry.getUpdatedAt();
        // Initialize  formatters for date and time
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM, yyyy");
        // Format the date and time
        String updateTimeString = timeFormatter.format(date).toString();
        String updateDateString = dateFormatter.format(date).toString();
        // Update views using the formatters
        holder.updateTimeTextView.setText(updateTimeString);
        holder.updateDateTextView.setText(updateDateString);
    }

    /** @return the number of elements in the entries*/
    @Override
    public int getItemCount() {
        return entries.size();
    }

    // An interface for item click actions
    public interface OnListItemClickListener {
        void onListItemClick(int id);
    }

    public List<DiaryEntry> getEntries() {
        return entries;
    }
}
