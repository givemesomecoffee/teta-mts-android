package ru.givemesomecoffee.tetamtsandroid.presenter

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl

import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import java.lang.Exception

class MovieDetailsPresenter(val view: MovieDetailsFragment) {
    private val movieModel = Movies(MoviesDataSourceImpl())
    private val categoryModel = Categories(MovieCategoriesDataSourceImpl())
    private val handler = CoroutineExceptionHandler { _, exception ->
       view.onGetDataFailure(exception.message)
        view.refresh?.isRefreshing = false
    }

    fun getData(id: Int?){
      view.viewLifecycleOwner.lifecycleScope.launch(handler){
            withContext(Dispatchers.IO){


              if (simulateNetwork() == 500)
                  throw Exception("Ошибка, перезагрузите страницу")
                view.movie = movieModel.getMovieById(id!!)
          if (simulateNetwork() == 500)
                   throw Exception("Ошибка, перезагрузите страницу")
                view.category = categoryModel.getCategoryById(view.movie.categoryId)

            }
            withContext(Dispatchers.Main){
                view.bindData()
                view.refresh?.isRefreshing = false
            }

        }
    }

}