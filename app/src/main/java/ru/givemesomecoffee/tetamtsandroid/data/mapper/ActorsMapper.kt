package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorDto
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.IMAGE_BASE_URL
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.ActorApi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.ActorUi

class ActorsMapper {

    @JvmName("fromApiToActorUi")
    fun toActorUi(list: List<ActorApi>): List<ActorUi> {
        return list.map {
            var img = it.profile_path
            if (!img.isNullOrEmpty()) {
                img = IMAGE_BASE_URL + it.profile_path
            }
            ActorUi(
                id = it.id.toInt(),
                name = it.name,
                imgUrl = img
            )
        }
    }


    fun toActorUi(list: List<ActorDto>): List<ActorUi> {
        return list.map {
            ActorUi(
                id = it.id,
                name = it.name,
                imgUrl = it.img
            )
        }
    }

    fun toActorDto(actors: List<ActorUi>): List<ActorDto> {
        return actors.map {
            ActorDto(
                name = it.name,
                img = it.imgUrl,
                id = it.id
            )
        }
    }
}
