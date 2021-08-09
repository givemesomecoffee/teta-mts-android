package ru.givemesomecoffee.tetamtsandroid.data.local.db.entity

import androidx.room.*


@Entity
class Actor(
    val name: String,
    val img: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "actorId")
    val id: Int?,
    )


@Entity(primaryKeys = ["movieId", "actorId"])
data class ActorsToMovies(
    val movieId: Int,
    val actorId: Int
)

data class MovieWithActors(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "actorId",
        associateBy = Junction(ActorsToMovies::class)
    )
    val actors: List<Actor>
)
