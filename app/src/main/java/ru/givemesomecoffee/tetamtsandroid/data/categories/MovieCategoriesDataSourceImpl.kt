package ru.givemesomecoffee.tetamtsandroid.data.categories

import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

class MovieCategoriesDataSourceImpl: MovieCategoriesDatasource {
    override fun getCategories() = listOf(
        CategoryDto("боевики"),
        CategoryDto("драмы"),
        CategoryDto("комедии"),
        CategoryDto("артхаус"),
        CategoryDto("мелодрамы"),
        CategoryDto("фантастика"),
        CategoryDto("триллеры")
    )
    }

