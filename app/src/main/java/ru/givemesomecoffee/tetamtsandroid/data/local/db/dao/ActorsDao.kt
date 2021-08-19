package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorsToMovies

@Dao
interface ActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActors(actors: List<Actor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActorToMovie(actorToMovie: ActorsToMovies)

}
