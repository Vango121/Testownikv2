package com.vango.testownik.room;

import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Po;
import com.vango.testownik.model.room.Pps;
import com.vango.testownik.model.room.Pps2;
import com.vango.testownik.model.room.Pt;
import com.vango.testownik.room.Dao.IzsDao;
import com.vango.testownik.room.Dao.MiernictwoDao;
import com.vango.testownik.room.Dao.PairDao;
import com.vango.testownik.room.Dao.PoDao;
import com.vango.testownik.room.Dao.Pps2Dao;
import com.vango.testownik.room.Dao.PpsDao;
import com.vango.testownik.room.Dao.PtDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Miernictwo.class, Pair.class, Pt.class, Pps.class, Pps2.class, Izs.class, Po.class}, version = 14)
@TypeConverters(Converter.class)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract MiernictwoDao miernictwoDao();
    public abstract PairDao pairDao();
    public abstract PtDao ptDao();
    public abstract PpsDao ppsDao();
    public abstract Pps2Dao pps2Dao();
    public abstract IzsDao izsDao();
    public abstract PoDao poDao();
}
