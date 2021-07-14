package ru.givemesomecoffee.tetamtsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.adapter.MovieAdapter
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration

const val CATEGORY = "category_key"

class MainActivity : AppCompatActivity() {
    private var category: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            category = savedInstanceState.getInt(CATEGORY, 0)
        }
        setContentView(R.layout.activity_movie_list)

        val movieList = findViewById<RecyclerView>(R.id.test)
        val manager = GridLayoutManager(this, 2)
        movieList.layoutManager = manager
        val model = Movies(MoviesDataSourceImpl())
        val movies = if (category == 1) getNewList(model) else getFullList(model)
        movieList.adapter = MovieAdapter(
            this,
            movies,
            itemClick = { movieTitle: String ->
                Toast.makeText(this, movieTitle, Toast.LENGTH_SHORT).show()
            })
        movieList.addItemDecoration(RecyclerItemDecoration(20, 55, 20))

        val categoryList = findViewById<RecyclerView>(R.id.movie_category_list)

        val categoryModel = Categories(MovieCategoriesDataSourceImpl())
        categoryList.adapter = CategoryAdapter(
            this,
            categoryModel.getCategories(),
            itemClick = { categoryTitle: String ->
                when (categoryTitle) {
                    "боевики" -> (movieList.adapter as MovieAdapter).updateMoviesList(
                        getNewList(model)
                    )
                    "Все категории" -> (movieList.adapter as MovieAdapter).updateMoviesList(
                        getFullList(model)
                    )
                    else -> Toast.makeText(this, categoryTitle, Toast.LENGTH_SHORT).show()
                }
            }
        )

        categoryList.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
    }


    private fun getNewList(model: Movies): List<MovieDto> {
        category = 1
        return listOf(model.getMovies()[0], model.getMovies()[3])
    }

    private fun getFullList(model: Movies): List<MovieDto> {
        category = 0
        return model.getMovies()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
    }
}


