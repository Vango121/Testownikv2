package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Pps2;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface Pps2Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Pps2 pps2);

    @Query("SELECT * FROM pps2_table")
    LiveData<List<Pps2>> getAllQuestions();
    @Update
    void update(Pps2 pps2);

    @Query("SELECT COUNT(id) FROM pps2_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE pps2_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
