package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor
import ru.givemesomecoffee.tetamtsandroid.domain.entity.ActorUi

class ActorsMapper {

    fun toActorUi(list: List<Actor>): List<ActorUi> {
        return list.map {
            ActorUi(
                id = it.id,
                name = it.name,
                imgUrl = it.img
            )
        }
    }
}

//TODO: singleton feels creepy, find best practises