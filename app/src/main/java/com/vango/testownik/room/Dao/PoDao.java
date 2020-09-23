package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
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
public interface PoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Po po);

    @Query("SELECT * FROM po_table")
    LiveData<List<Po>> getAllQuestions();
    @Update
    void update(Po po);

    @Query("SELECT COUNT(id) FROM po_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE po_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD);
}
