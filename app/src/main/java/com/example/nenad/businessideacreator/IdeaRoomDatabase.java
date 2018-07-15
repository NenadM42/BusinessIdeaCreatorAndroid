package com.example.nenad.businessideacreator;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Idea.class}, version = 5)
public abstract class IdeaRoomDatabase extends RoomDatabase {

    public abstract IdeaDao ideaDao();

    private static IdeaRoomDatabase INSTANCE;

    static IdeaRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (IdeaRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            IdeaRoomDatabase.class,"idea_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final IdeaDao mDao;

        PopulateDbAsync(IdeaRoomDatabase db){
            mDao = db.ideaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //mDao.deleteAll();

            if(mDao.count() == 0){
                Idea idea = new Idea("Idea Example");
                mDao.insert(idea);
            }

            return null;
        }
    }
}

