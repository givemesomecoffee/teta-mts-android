package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorsToMovies

@Dao
interface ActorsDao {
    @Insert
    fun setActors(actors: List<Actor>)

    @Insert
    fun setActorsToMovies(actorsToMovies: List<ActorsToMovies>)
}