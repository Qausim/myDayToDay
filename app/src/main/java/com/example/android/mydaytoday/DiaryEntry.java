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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by HP on 6/26/2018.
 */
// This class handles database entries

// Add @Entity annotation to make and set the table name to "diary"
@Entity(tableName = "diary")
public class DiaryEntry {
    // id is an integer variable that stores a unique id for each entry.
    // Annotate id as the primary key and set autogenerate to true.
    @PrimaryKey(autoGenerate = true)
    private int id;
    // activities is a String variable that stores the activities done each day.
    private String activities, creationDateString;
    // updatedAt is a Date object for the time a new task is created.
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // Ignore this constructor so the other one is used.
    /**@Constructor handles creation of new DailyEntry objects.
     * @param activities
     * @param updatedAt
     * @param creationDateString
     */
    @Ignore
    public DiaryEntry(String activities, String creationDateString, Date updatedAt) {
        this.activities = activities;
        this.updatedAt = updatedAt;
        this.creationDateString = creationDateString;
    }

    /**@Constructor handles creation of new DailyEntry objects.
     *
     * @param id
     * @param activities
     * @param updatedAt
     * @param creationDateString
     */
    public DiaryEntry(int id, String activities, String creationDateString, Date updatedAt) {
       this.id = id;
       this.activities = activities;
       this.creationDateString = creationDateString;
       this.updatedAt = updatedAt;
    }

    /**@set the id of a new DailyEntry object.
     * @param id*/
    public void setId(int id) { this.id = id; }

    /**@sets the activities of a new DailyEntry object.
     * @param activities*/
    public void setActivities(String activities) { this.activities = activities; }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    /**@sets the activities of a new DailyEntry object.
     * @param updatedAt*/
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    /**@returns the id of a DailyEntry object.*/
    public int getId() { return this.id; }

    /**@returns the activities of a DailyEntry object.*/
    public String getActivities() { return this.activities; }

    public String getCreationDateString() {
        return this.creationDateString;
    }

    /**@returns the date of a DailyEntry object's update.*/
    public Date getUpdatedAt() { return this.updatedAt; }
}
