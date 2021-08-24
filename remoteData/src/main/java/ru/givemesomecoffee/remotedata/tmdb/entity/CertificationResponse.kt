package ru.givemesomecoffee.remotedata.tmdb.entity

import java.util.*

class CertificationResponse(val results: List<Country>)

data class Country(val iso_3166_1: String, val release_dates: List<Certification>)

data class Certification(val certification: String, val release_date: Date)



