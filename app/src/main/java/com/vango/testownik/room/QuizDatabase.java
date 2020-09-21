package com.vango.testownik.room;

import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.room.Dao.MiernictwoDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Miernictwo.class, version = 12)
@TypeConverters(Converter.class)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract MiernictwoDao miernictwoDao();
}
