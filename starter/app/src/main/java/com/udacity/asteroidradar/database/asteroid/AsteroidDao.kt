package com.udacity.asteroidradar.database.asteroid

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Constants

@Dao
interface AsteroidDao {

    @Query("SELECT * from ${Constants.ASTEROID_TABLE_NAME} WHERE closeApproachDate >= :today ORDER BY closeApproachDate")
    fun getAsteroids (today : String) : LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids (vararg asteroids: DatabaseAsteroid)
}