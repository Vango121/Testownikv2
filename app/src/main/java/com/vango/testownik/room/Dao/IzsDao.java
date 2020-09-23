package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Po;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IzsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Izs izs);

    @Query("SELECT * FROM izs_table")
    LiveData<List<Izs>> getAllQuestions();
    @Update
    void update(Izs izs);

    @Query("SELECT COUNT(id) FROM izs_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE izs_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
