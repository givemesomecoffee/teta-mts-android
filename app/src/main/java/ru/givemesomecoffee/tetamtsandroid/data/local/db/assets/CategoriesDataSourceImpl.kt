package ru.givemesomecoffee.tetamtsandroid.data.local.db.assets

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category


class CategoriesDataSourceImpl {
    fun getCategories() = listOf(
        Category("боевики", 1),
        Category("драмы", 2),
        Category("комедии", 3),
        Category("артхаус", 4),
        Category("мелодрамы", 5),
        Category("фантастика", 6),
        Category("триллеры", 7)
    )
}

