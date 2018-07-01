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

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by HP on 6/26/2018.
 */
// The Dao for data access
@Dao
public interface DiaryDao {

    // For query operations
    @Query("SELECT * FROM diary ORDER BY id")
    List<DiaryEntry> loadAllDays();

    // For daily insertion
    @Insert
    void insertDay(DiaryEntry diaryEntry);

    // For updates
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDay(DiaryEntry diaryEntry);

    // For deletion
    @Delete
    void deleteDay(DiaryEntry diaryEntry);
}
