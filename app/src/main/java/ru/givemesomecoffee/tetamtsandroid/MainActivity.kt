package ru.givemesomecoffee.tetamtsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
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

        val moviesListView = findViewById<RecyclerView>(R.id.movies_list)
        val manager = GridLayoutManager(this, 2)
        moviesListView.layoutManager = manager
        val moviesModel = Movies(MoviesDataSourceImpl())
        val moviesList =
            if (category != 0) getMoviesListByCategory(moviesModel, category)
            else getAllMoviesList(moviesModel)
        moviesListView.adapter = MovieAdapter(
            this,
            moviesList,
            itemClick = { movieTitle: String ->
                Toast.makeText(this, movieTitle, Toast.LENGTH_SHORT).show()
            })
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(
                spacingBottom = 50,
                isMovieList = true
            )
        )

        val categoriesListView = findViewById<RecyclerView>(R.id.movie_category_list)
        val categoriesModel = Categories(MovieCategoriesDataSourceImpl())
        categoriesListView.adapter = CategoryAdapter(
            this,
            categoriesModel.getCategories(),
            itemClick = { categoryId: Int ->
                when (categoryId) {
                    0 -> (moviesListView.adapter as MovieAdapter).updateMoviesList(
                        getAllMoviesList(moviesModel)
                    )
                    else -> (moviesListView.adapter as MovieAdapter).updateMoviesList(
                        getMoviesListByCategory(moviesModel, categoryId)
                    )
                }
            }
        )
        categoriesListView.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
    }


    private fun getMoviesListByCategory(model: Movies, id: Int): List<MovieDto> {
        category = id
        val list = model.geMoviesByCategory(category)
        isMoviesListEmpty(list)
        return list
    }

    private fun getAllMoviesList(model: Movies): List<MovieDto> {
        category = 0
        val list = model.getMovies()
        isMoviesListEmpty(list)
        return list
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
    }

    private fun isMoviesListEmpty(list: List<MovieDto>) {
        findViewById<TextView>(R.id.empty_movies_list).visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}


