package com.udacity.asteroidradar

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"

    //API Constants
    const val ASTEROIDS_ENDPOINT = "neo/rest/v1/feed"
    const val IOTD_ENDPOINT = "planetary/apod"

    //Database Constants
    const val ASTEROID_TABLE_NAME = "asteroids_table"
    const val PICTURE_OF_DAY_TABLE = "picture_table"
}