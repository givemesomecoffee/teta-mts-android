package ru.givemesomecoffee.tetamtsandroid
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

class MoviesListFragment: Fragment() {
    private var moviesListFragmentClickListener: MoviesListFragmentClickListener? = null

    companion object {
            const val MOVIE_LIST_TAG = "MovieList"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_movie_list, container, false)
        val moviesListView = view.findViewById<RecyclerView>(R.id.movies_list)
        val manager = GridLayoutManager(view.context, 2)
        moviesListView.layoutManager = manager
        val moviesModel = Movies(MoviesDataSourceImpl())
        val moviesList = moviesModel.getMovies()
           // if (category != 0) getMoviesListByCategory(moviesModel, category)
           // else
           // getAllMoviesList(moviesModel)

        moviesListView.adapter = MovieAdapter(
            view.context,
            moviesList,
            itemClick = {  categoryId: Int ->
                moviesListFragmentClickListener?.onMovieCardClicked(categoryId)
            })
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(
                spacingBottom = 50,
                isMovieList = true
            )
        )
        val categoriesListView = view.findViewById<RecyclerView>(R.id.movie_category_list)
        val categoriesModel = Categories(MovieCategoriesDataSourceImpl())
        categoriesListView.adapter = CategoryAdapter(
            view.context,
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


        return view
    }
/*
    if (savedInstanceState != null) {
        category = savedInstanceState.getInt(CATEGORY, 0)
    }

*/





   private fun getMoviesListByCategory(model: Movies, id: Int): List<MovieDto> {
      //  category = id
        val list = model.geMoviesByCategory(id)
    isMoviesListEmpty(list)
        return list
        }

private fun getAllMoviesList(model: Movies): List<MovieDto> {
       // category = 0
        val list = model.getMovies()
       isMoviesListEmpty(list)
        return list
        }

/*        override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
        }*/

private fun isMoviesListEmpty(list: List<MovieDto>) {

       requireView().findViewById<TextView>(R.id.empty_movies_list).visibility  =
        if (list.isEmpty()) View.VISIBLE else View.GONE
        }

    interface MoviesListFragmentClickListener {
        fun onMovieCardClicked(id: Int)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesListFragmentClickListener){
            moviesListFragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        moviesListFragmentClickListener = null
    }



}
