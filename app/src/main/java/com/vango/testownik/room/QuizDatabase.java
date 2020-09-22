package com.vango.testownik.room;

import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.room.Dao.MiernictwoDao;
import com.vango.testownik.room.Dao.PairDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Miernictwo.class, Pair.class}, version = 13)
@TypeConverters(Converter.class)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract MiernictwoDao miernictwoDao();
    public abstract PairDao pairDao();
}
