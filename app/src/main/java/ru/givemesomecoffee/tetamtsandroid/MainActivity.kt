package ru.givemesomecoffee.tetamtsandroid

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.adapter.MovieAdapter
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val manager = GridLayoutManager(this, 2)
        val movieList = findViewById<RecyclerView>(R.id.test)
       movieList.layoutManager = manager
        val model = Movies(MoviesDataSourceImpl())
        movieList.adapter = MovieAdapter(
            this,
            model.getMovies(),
            itemClick = { movieTitle: String ->
                Toast.makeText(this, movieTitle, Toast.LENGTH_SHORT).show()
            })
        movieList.addItemDecoration(RecyclerItemDecoration(20, 55, 20))
        val categoryList = findViewById<RecyclerView>(R.id.movie_category_list)
        val categoryModel = Categories(MovieCategoriesDataSourceImpl())
        categoryList.adapter = CategoryAdapter(this, categoryModel.getCategories())
        categoryList.addItemDecoration(RecyclerItemDecoration(6, 0, 20))

    }


}