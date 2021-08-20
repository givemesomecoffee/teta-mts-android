package ru.givemesomecoffee.remotedata.tmdb.entity

class GenresResponse (val genres: List<Genre>)

class Genre(val id: Int, val name: String)