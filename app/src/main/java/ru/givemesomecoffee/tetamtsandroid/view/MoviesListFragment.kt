package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.adapter.MoviesListAdapter
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration
import java.lang.Exception
import java.lang.Thread.sleep

class MoviesListFragment : Fragment() {
    private var moviesListFragmentClickListener: MoviesListFragmentClickListener? = null
    private var category = 0
    private val moviesModel = Movies(MoviesDataSourceImpl())
    private val categoriesModel = Categories(MovieCategoriesDataSourceImpl())
    private lateinit var moviesListView: RecyclerView
    private lateinit var categoriesListView: RecyclerView
    private lateinit var moviesAdapter: MoviesListAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    private fun init() {
        moviesListView = requireView().findViewById(R.id.movies_list)
        categoriesListView = requireView().findViewById(R.id.movie_category_list)
        moviesAdapter = setMoviesListAdapter(moviesModel)
        categoriesAdapter = CategoryAdapter(
            categoriesModel.getCategories(),
            itemClick = { categoryId: Int ->
                updateMoviesListByCategory(categoryId, moviesListView, moviesModel)
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesListFragmentClickListener) {
            moviesListFragmentClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            category = savedInstanceState.getInt(CATEGORY, 0)
        }
        init()
        moviesListView.layoutManager = GridLayoutManager(view.context, 2)
        moviesListView.adapter = moviesAdapter
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(spacingBottom = 50, isMovieList = true)
        )
        categoriesListView.adapter = categoriesAdapter
        categoriesListView.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
        getMovies()
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_container).setOnRefreshListener {
            getMovies()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
    }

    override fun onDetach() {
        super.onDetach()
        moviesListFragmentClickListener = null
    }

    private fun setMoviesListAdapter(moviesModel: Movies): MoviesListAdapter {
        val moviesList =
            if (category != 0) getMoviesListByCategory(moviesModel, category) else getAllMoviesList(
                moviesModel
            )

        return MoviesListAdapter(
            listOf(),
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

    private fun getMovies() {
        val handler = CoroutineExceptionHandler { _, exception ->
            Toast.makeText(view?.context, "handled ${exception.message}", Toast.LENGTH_SHORT).show()
            requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_container).isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launch(handler) {
            val listForErrorTest = listOf(100L, 200L, 3000L)
            val errorMaker = listForErrorTest.random()
             if (errorMaker > 200L) {
                    throw Exception("Сервер не отвечает, попробуйте снова")
              }
            val list = withContext(Dispatchers.IO) { moviesModel.getMovies() }
            val list1 = withContext(Dispatchers.IO) { list!!.shuffled().take(10) }
            withContext(Dispatchers.IO) { sleep(errorMaker) }
            withContext(Dispatchers.Main) {
                moviesAdapter.updateMoviesList(
                    list1
                )
            }
            requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_container).isRefreshing = false
            // moviesListView.smoothScrollToPosition(0)
            moviesListView.scrollToPosition(0)
        }
    }

    companion object {
        const val MOVIE_LIST_TAG = "MovieList"
        const val CATEGORY = "category_key"
    }
}
