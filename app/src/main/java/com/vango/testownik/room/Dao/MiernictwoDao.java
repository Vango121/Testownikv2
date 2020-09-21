package com.vango.testownik.room.Dao;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.room.Miernictwo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MiernictwoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Miernictwo miernictwo);

    @Query("SELECT * FROM miernictwo_table")
    LiveData<List<Miernictwo>> getAllQuestions();
    @Update
    void update(Miernictwo miernictwo);

    @Query("SELECT COUNT(id) FROM miernictwo_table")
    LiveData<Integer> getRowCount();

    @Query("UPDATE miernictwo_table SET question = :question , answerA = :answerA, answerB = :answerB, answerC= :answerC, answerD= :answerD WHERE id = :itemid  ")
    void updateWithoutCount(Integer itemid,String question, Answer answerA,Answer answerB,Answer answerC,Answer answerD);
}
