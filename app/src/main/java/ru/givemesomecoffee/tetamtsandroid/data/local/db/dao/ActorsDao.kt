package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorDto
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorsToMovies

@Dao
interface ActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActors(actors: List<ActorDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActorToMovie(actorToMovie: ActorsToMovies)

}
