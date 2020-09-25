package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Kodowanie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface KodowanieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Kodowanie kodowanie);

    @Query("SELECT * FROM kodowanie_table")
    LiveData<List<Kodowanie>> getAllQuestions();
    @Update
    void update(Kodowanie kodowanie);

    @Query("SELECT COUNT(id) FROM kodowanie_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE kodowanie_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
