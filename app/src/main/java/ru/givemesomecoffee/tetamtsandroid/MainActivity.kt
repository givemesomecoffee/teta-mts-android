package ru.givemesomecoffee.tetamtsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.givemesomecoffee.tetamtsandroid.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.adapter.MovieAdapter
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        val manager = GridLayoutManager(this, 2)
        val movieList = findViewById<RecyclerView>(R.id.test)
       movieList.layoutManager = manager
        val model = Movies(MoviesDataSourceImpl())
        movieList.adapter = MovieAdapter(this, model.getMovies())
        //movieList.addItemDecoration(MovieAdapter.RecyclerItemDecoration(2, 20))
        val categoryList = findViewById<RecyclerView>(R.id.movie_category_list)
        val categoryModel = Categories(MovieCategoriesDataSourceImpl())
        categoryList.adapter = CategoryAdapter(this, categoryModel.getCategories())
        categoryList.addItemDecoration(CategoryAdapter.RecyclerItemDecoration(1, 6))
        movieList.addItemDecoration(MovieAdapter.RecyclerItemDecoration(1, 55))
    }
}