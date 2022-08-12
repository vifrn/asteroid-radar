package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.asteroid.DatabaseAsteroid
import com.udacity.asteroidradar.database.iodt.DatabasePictureOfDay

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return this.map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }
}

fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return this.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}

fun DatabasePictureOfDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(mediaType, title, url)
}

fun PictureOfDay.asDatabaseModel() : DatabasePictureOfDay {
    return DatabasePictureOfDay(mediaType, url, title)
}