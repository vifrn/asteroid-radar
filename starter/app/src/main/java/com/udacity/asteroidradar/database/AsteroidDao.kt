package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Constants

@Dao
interface AsteroidDao {

    @Query("SELECT * from ${Constants.ASTEROID_TABLE_NAME}")
    fun getAsteroids () : LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids (vararg asteroids: DatabaseAsteroid)
}