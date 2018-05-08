package com.castellon.racl9.devhyr.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.castellon.racl9.devhyr.room.dao.productsDao;
import com.castellon.racl9.devhyr.room.models.productsDB;
import com.castellon.racl9.devhyr.room.models.productsSavedDB;

@Database(entities = {productsDB.class,productsSavedDB.class}, version = 1)
public abstract class database extends RoomDatabase {

    public abstract productsDao productsDao();
}
