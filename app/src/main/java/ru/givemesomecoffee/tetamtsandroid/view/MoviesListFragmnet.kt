package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.adapter.MoviesListAdapter
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration

class MoviesListFragment : Fragment() {
    private var moviesListFragmentClickListener: MoviesListFragmentClickListener? = null
    private var category = 0

    companion object {
        const val MOVIE_LIST_TAG = "MovieList"
        const val CATEGORY = "category_key"
    }

    interface MoviesListFragmentClickListener {
        fun onMovieCardClicked(id: Int)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            category = savedInstanceState.getInt(CATEGORY, 0)
        }

        val moviesListView = view.findViewById<RecyclerView>(R.id.movies_list)
        moviesListView.layoutManager = GridLayoutManager(view.context, 2)
        val moviesModel = Movies(MoviesDataSourceImpl())
        moviesListView.adapter = setMoviesListAdapter(moviesModel)
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(spacingBottom = 50, isMovieList = true)
        )

        val categoriesListView = view.findViewById<RecyclerView>(R.id.movie_category_list)
        val categoriesModel = Categories(MovieCategoriesDataSourceImpl())
        categoriesListView.adapter = CategoryAdapter(
            categoriesModel.getCategories(),
            itemClick = { categoryId: Int ->
                updateMoviesListByCategory(categoryId, moviesListView, moviesModel)
            }
        )
        categoriesListView.addItemDecoration(RecyclerItemDecoration(6, 0, 20))

    }

    private fun setMoviesListAdapter(moviesModel: Movies): MoviesListAdapter {
        val moviesList =
            if (category != 0) getMoviesListByCategory(moviesModel, category) else getAllMoviesList(
                moviesModel
            )

        return MoviesListAdapter(
            moviesList,
            itemClick = { movieId: Int ->
                moviesListFragmentClickListener?.onMovieCardClicked(movieId)
            })
    }

    private fun updateMoviesListByCategory(
        categoryId: Int,
        moviesListView: RecyclerView,
        moviesModel: Movies
    ) {
        when (categoryId) {
            0 -> (moviesListView.adapter as MoviesListAdapter).updateMoviesList(
                getAllMoviesList(moviesModel)
            )
            else -> (moviesListView.adapter as MoviesListAdapter).updateMoviesList(
                getMoviesListByCategory(moviesModel, categoryId)
            )
        }
    }

    private fun getMoviesListByCategory(model: Movies, id: Int): List<MovieDto> {
        category = id
        val list = model.geMoviesByCategory(id)
        setViewForEmptyList(list)
        return list
    }

    private fun getAllMoviesList(model: Movies): List<MovieDto> {
        category = 0
        val list = model.getMovies()
        setViewForEmptyList(list)
        return list
    }

    private fun setViewForEmptyList(list: List<MovieDto>) {
        requireView().findViewById<TextView>(R.id.empty_movies_list).visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesListFragmentClickListener) {
            moviesListFragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        moviesListFragmentClickListener = null
    }
}
