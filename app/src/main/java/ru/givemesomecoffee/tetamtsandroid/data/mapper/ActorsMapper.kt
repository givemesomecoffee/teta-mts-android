package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.ActorApi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.ActorUi

class ActorsMapper {

    fun toActorUi(list: List<ActorApi>): List<ActorUi> {
        return list.map {
            var img = it.profile_path
            if (!img.isNullOrEmpty()) {
                img = "https://image.tmdb.org/t/p/original" + it.profile_path
            }
            ActorUi(
                id = null,
                name = it.name,
                imgUrl = img
            )
        }
    }
}
