package ru.givemesomecoffee.tetamtsandroid.data.remote.entity

class CertificationResponse(val results: List<Country>)

data class Country(val iso_3166_1: String, val release_dates: List<Certification>)

data class Certification(val certification: String)



