package com.example.nenad.businessideacreator;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class IdeaViewModel extends AndroidViewModel{

    private IdeaRepository mRepository;

    private LiveData<List<Idea>> mAllIdeas;

    public IdeaViewModel(Application application){
        super(application);
        mRepository = new IdeaRepository(application);
        mAllIdeas = mRepository.getAllIdeas();
    }

    LiveData<List<Idea>> getAllIdeas(){
        return mAllIdeas;
    }

    public void insert(Idea idea){
        mRepository.insert(idea);
    }

    public Idea getIdeaViaId(int id){
        List<Idea> mIdeaList = getAllIdeas().getValue();

        for(Idea idea : mIdeaList){
            if(idea.getId() == id){
                return idea;
            }
        }
        return null;
    }

    public void delete(Idea idea){
        mRepository.delete(idea);
    }
}