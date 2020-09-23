package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Pps;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PpsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Pps pps);

    @Query("SELECT * FROM pps_table")
    LiveData<List<Pps>> getAllQuestions();
    @Update
    void update(Pps pps);

    @Query("SELECT COUNT(id) FROM pps_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE pps_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
