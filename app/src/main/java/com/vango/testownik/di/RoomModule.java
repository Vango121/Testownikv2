package com.vango.testownik.di;

import android.content.Context;

import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.room.Dao.IzsDao;
import com.vango.testownik.room.Dao.KodowanieDao;
import com.vango.testownik.room.Dao.MiernictwoDao;
import com.vango.testownik.room.Dao.PairDao;
import com.vango.testownik.room.Dao.PoDao;
import com.vango.testownik.room.Dao.Pps2Dao;
import com.vango.testownik.room.Dao.PpsDao;
import com.vango.testownik.room.Dao.PtDao;
import com.vango.testownik.room.QuizDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
@Module
@InstallIn(ApplicationComponent.class)
public class RoomModule {

    @Singleton
    @Provides
    QuizDatabase provideDb(@ApplicationContext Context context) {
        return  Room
                .databaseBuilder(
                        context,
                        QuizDatabase.class,
                "quiz_db")
                .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    MiernictwoDao provideMiernictwoDao(QuizDatabase quizDatabase){
        return quizDatabase.miernictwoDao();
    }
    @Singleton
    @Provides
    PairDao providePairDao(QuizDatabase quizDatabase){
        return quizDatabase.pairDao();
    }

    @Singleton
    @Provides
    PtDao providePtDao(QuizDatabase quizDatabase){
        return quizDatabase.ptDao();
    }

    @Singleton
    @Provides
    PpsDao providePpsDao(QuizDatabase quizDatabase){
        return quizDatabase.ppsDao();
    }

    @Singleton
    @Provides
    Pps2Dao providePps2Dao(QuizDatabase quizDatabase){
        return quizDatabase.pps2Dao();
    }

    @Singleton
    @Provides
    IzsDao provideIzsDao(QuizDatabase quizDatabase){
        return quizDatabase.izsDao();
    }

    @Singleton
    @Provides
    PoDao providePoDao(QuizDatabase quizDatabase){
        return quizDatabase.poDao();
    }

    @Singleton
    @Provides
    KodowanieDao provideKodowanieDao(QuizDatabase quizDatabase){
        return quizDatabase.kodowanieDao();
    }

}
