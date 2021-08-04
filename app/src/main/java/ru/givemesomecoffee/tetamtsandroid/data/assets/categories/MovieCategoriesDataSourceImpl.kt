package ru.givemesomecoffee.tetamtsandroid.data.assets.categories

import ru.givemesomecoffee.tetamtsandroid.data.entity.Category

class MovieCategoriesDataSourceImpl : MovieCategoriesDatasource {
    override fun getCategories() = listOf(
        Category("боевики", 1),
        Category("драмы", 2),
        Category("комедии", 3),
        Category("артхаус", 4),
        Category("мелодрамы", 5),
        Category("фантастика", 6),
        Category("триллеры", 7)
    )
}

