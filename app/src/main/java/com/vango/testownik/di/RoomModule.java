package com.vango.testownik.di;

import android.content.Context;

import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.room.Dao.MiernictwoDao;
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
}
