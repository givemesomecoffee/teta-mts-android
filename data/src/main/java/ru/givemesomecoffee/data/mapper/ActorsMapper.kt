package ru.givemesomecoffee.data.mapper

import ru.givemesomecoffee.data.entity.ActorUi
import ru.givemesomecoffee.localdata.db.entity.ActorDto
import ru.givemesomecoffee.remotedata.tmdb.IMAGE_BASE_URL
import ru.givemesomecoffee.remotedata.tmdb.entity.ActorApi

internal class ActorsMapper {

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
                imgUrl = img,
                order = it.order
            )
        }
    }

    fun toActorUi(list: List<ActorDto>): List<ActorUi> {
        val result =  list.map {
            ActorUi(
                id = it.id,
                name = it.name,
                imgUrl = it.img,
                order = it.order
            )
        }
        return result.sortedBy { actor -> actor.order }
    }

    fun toActorDto(actors: List<ActorUi>): List<ActorDto> {
         return actors.map {
            ActorDto(
                name = it.name,
                img = it.imgUrl,
                id = it.id,
                order = it.order
            )
        }
    }
}
