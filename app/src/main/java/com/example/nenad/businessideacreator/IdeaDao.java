package com.example.nenad.businessideacreator;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface IdeaDao {


    @Insert
    void insert(Idea idea);

    @Query("DELETE FROM idea_table")
    void deleteAll();

    @Query("SELECT * from idea_table ORDER BY mTitle ASC")
    LiveData<List<Idea>> getAllIdeas();

    @Delete
    void delete(Idea idea);

    @Query("SELECT COUNT(*) FROM idea_table")
    int count();

}
