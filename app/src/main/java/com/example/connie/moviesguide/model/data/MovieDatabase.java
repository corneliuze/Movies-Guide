package com.example.connie.moviesguide.model.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract  class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public static volatile MovieDatabase INSTANCE;

    static MovieDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (MovieDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class,"movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
