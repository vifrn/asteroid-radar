package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid

data class NetworkAsteroid (
    val id: Long,
    @Json(name = "name") val codename: String,
    @Json(name = "close_approach_date") val closeApproachDate: String,
    @Json(name = "absolute_magnitude") val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter_max") val estimatedDiameter: Double,
    @Json(name = "kilometers_per_second") val relativeVelocity: Double,
    @Json(name = "astronomical") val distanceFromEarth: Double,
    @Json(name = "is_potentially_hazardous_asteroid") val isPotentiallyHazardous: Boolean)

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

/**
 * Convert Network results to database objects
 */
fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid> {
    return asteroids.map {
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

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    return asteroids.map {
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