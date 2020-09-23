package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Pt;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Pt pt);

    @Query("SELECT * FROM pt_table")
    LiveData<List<Pt>> getAllQuestions();
    @Update
    void update(Pt pt);

    @Query("SELECT COUNT(id) FROM pt_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE pt_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
