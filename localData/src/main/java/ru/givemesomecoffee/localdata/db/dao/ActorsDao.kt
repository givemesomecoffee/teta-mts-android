package ru.givemesomecoffee.localdata.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.givemesomecoffee.localdata.db.entity.ActorDto
import ru.givemesomecoffee.localdata.db.entity.ActorsToMovies


@Dao
interface ActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActors(actors: List<ActorDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActorToMovie(actorToMovie: ActorsToMovies)

}
