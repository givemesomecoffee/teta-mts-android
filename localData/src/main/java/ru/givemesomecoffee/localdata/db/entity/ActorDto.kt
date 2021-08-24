package ru.givemesomecoffee.localdata.db.entity

import androidx.room.*

@Entity
class ActorDto(
    val name: String,
    val img: String?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "actorId")
    val id: Int?,
    val order: Int
)

@Entity(primaryKeys = ["movieId", "actorId"])
data class ActorsToMovies(
    val movieId: Int,
    val actorId: Int
)

data class MovieWithActors(
    @Embedded
    val movie: MovieDto,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "actorId",
        associateBy = Junction(ActorsToMovies::class)
    )
    val actors: List<ActorDto>
)
