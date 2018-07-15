package com.example.nenad.businessideacreator;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class IdeaRepository {

    private IdeaDao mIdeaDao;
    private LiveData<List<Idea>> mAllIdeas;

    IdeaRepository(Application application){
        IdeaRoomDatabase db = IdeaRoomDatabase.getDatabase(application);
        mIdeaDao = db.ideaDao();
        mAllIdeas = mIdeaDao.getAllIdeas();
    }

    LiveData<List<Idea>> getAllIdeas(){
        return mAllIdeas;
    }

    public void insert(Idea idea){
        new insertAsyncTask(mIdeaDao).execute(idea);
    }

    public void delete(Idea idea){
        new deleteAsyncTask(mIdeaDao).execute(idea);
    }

    private static class insertAsyncTask extends AsyncTask<Idea, Void, Void>{

        private IdeaDao mAsyncTaskDao;

        insertAsyncTask(IdeaDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            mAsyncTaskDao.insert(ideas[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Idea, Void, Void>{

        private IdeaDao mAsyncTaskDao;

        deleteAsyncTask(IdeaDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            mAsyncTaskDao.delete(ideas[0]);
            return null;
        }
    }

}
