package com.castellon.racl9.devhyr.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.castellon.racl9.devhyr.room.models.productsDB;
import com.castellon.racl9.devhyr.room.models.productsSavedDB;

import java.util.List;

@Dao
public interface productsDao {

    @Query("SELECT * FROM productsDB")
    List<productsDB> getAllProduct();

    @Insert
    void insertAll(productsDB... product);

    @Query("SELECT * FROM productsSavedDB")
    List<productsSavedDB> getAllProductSaved();

    @Insert
    void insertAllPS(productsSavedDB... product);
}
