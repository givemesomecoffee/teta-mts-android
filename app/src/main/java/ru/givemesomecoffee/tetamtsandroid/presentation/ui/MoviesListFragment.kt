package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.presentation.adapter.MoviesListAdapter
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.presenter.MovieDetailsViewModel
import ru.givemesomecoffee.tetamtsandroid.presentation.presenter.MoviesListViewModel
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration


class MoviesListFragment : Fragment() {
    private var moviesListFragmentClickListener: MoviesListFragmentClickListener? = null
    //private var moviesListViewModel: MoviesListViewModel = MoviesListViewModel(this)
    private lateinit var moviesListView: RecyclerView
    private lateinit var categoriesListView: RecyclerView
    private var moviesAdapter: MoviesListAdapter? = null
    private var categoriesAdapter: CategoryAdapter? = null
    private var emptyListView: TextView? = null
    private var moviesRefreshSwipeView: SwipeRefreshLayout? = null
    private lateinit var errorHandlerView: TextView
    private var moviesList: List<MovieUi>? = null
    private var category = 0
    private val viewModel: MoviesListViewModel by viewModels()

    private fun init() {
        Log.d("test", "i was born")
        Log.d("test", MoviesListFragment.toString())
        moviesListView = requireView().findViewById(R.id.movies_list)
        categoriesListView = requireView().findViewById(R.id.movie_category_list)
        emptyListView = requireView().findViewById(R.id.empty_movies_list)
        moviesRefreshSwipeView = requireView().findViewById(R.id.swipe_container)
        errorHandlerView = requireView().findViewById(R.id.error_handler)
        moviesAdapter = setMoviesListAdapter()
        moviesListView.adapter = moviesAdapter
        categoriesAdapter = setCategoryAdapter()
        categoriesListView.adapter = categoriesAdapter
      //  moviesListViewModel.updateMoviesListByCategory(category, this)
        viewModel.data.observe(viewLifecycleOwner, Observer(::setNewMoviesList))
        viewModel.categories.observe(viewLifecycleOwner, Observer(::setNewCategoriesList))
        viewModel.updateCategories(this)
        viewModel.updateMoviesListByCategory(0, this)

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
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(spacingBottom = 50, isMovieList = true)
        )
        categoriesListView.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
        moviesRefreshSwipeView?.setOnRefreshListener {
            errorHandlerView.visibility = View.INVISIBLE
            viewModel.updateMoviesListByCategory(category, this)
            moviesRefreshSwipeView?.isRefreshing = false
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

    private fun setMoviesListAdapter(): MoviesListAdapter {
        return MoviesListAdapter(
            listOf(),
            itemClick = { movieId: Int ->
                moviesListFragmentClickListener?.onMovieCardClicked(movieId)
            })
    }

    private fun setCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter(
            listOf(),
            itemClick = { categoryId: Int -> onCategoryClicked(categoryId) })
    }

    private fun onCategoryClicked(categoryId: Int) {
        errorHandlerView.visibility = View.INVISIBLE
        category = categoryId
        viewModel.updateMoviesListByCategory(categoryId, this)
    }

    fun onGetDataFailure(message: String?) {
        moviesRefreshSwipeView?.isRefreshing = false
        if (moviesList == null) {
            errorHandlerView.visibility = View.VISIBLE
        }
        Toast.makeText(view?.context, "$message", Toast.LENGTH_SHORT).show()
    }

    fun setNewMoviesList(await: List<MovieUi>) {
        moviesList = await
        emptyListView?.visibility = if (await.isEmpty()) View.VISIBLE else View.GONE
        moviesAdapter?.updateMoviesList(await)
        moviesRefreshSwipeView?.isRefreshing = false
        moviesListView.scrollToPosition(0)
    }

    fun setNewCategoriesList(await: List<CategoryUi>) {
        categoriesAdapter?.updateCategoriesList(await)
        categoriesListView.scrollToPosition(0)
    }

    companion object {
        const val MOVIE_LIST_TAG = "MovieList"
        const val CATEGORY = "category_key"
    }
}
