package ru.givemesomecoffee.tetamtsandroid.data.categories

import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

class MovieCategoriesDataSourceImpl : MovieCategoriesDatasource {
    override fun getCategories() = listOf(
        CategoryDto("боевики", 1),
        CategoryDto("драмы", 2),
        CategoryDto("комедии", 3),
        CategoryDto("артхаус", 4),
        CategoryDto("мелодрамы", 5),
        CategoryDto("фантастика", 6),
        CategoryDto("триллеры", 7)
    )
}

