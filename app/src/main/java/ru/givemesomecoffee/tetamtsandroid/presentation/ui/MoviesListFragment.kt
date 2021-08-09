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
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.CategoryAdapter
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.MoviesListAdapter
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.MoviesListViewModel
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration

class MoviesListFragment : Fragment() {
    private var moviesListFragmentClickListener: MoviesListFragmentClickListener? = null
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
        moviesListView = requireView().findViewById(R.id.movies_list)
        categoriesListView = requireView().findViewById(R.id.movie_category_list)
        emptyListView = requireView().findViewById(R.id.empty_movies_list)
        moviesRefreshSwipeView = requireView().findViewById(R.id.swipe_container)
        errorHandlerView = requireView().findViewById(R.id.error_handler)
        moviesAdapter = setMoviesListAdapter()
        moviesListView.adapter = moviesAdapter
        categoriesAdapter = setCategoryAdapter()
        categoriesListView.adapter = categoriesAdapter
        viewModel.data.observe(viewLifecycleOwner, Observer(::setNewMoviesList))
        viewModel.categories.observe(viewLifecycleOwner, Observer(::setNewCategoriesList))
        viewModel.loadingState.observe(viewLifecycleOwner, Observer(::onLoading))
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
        Log.d("navigationFragments", "view list is created")
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            category = savedInstanceState.getInt(CATEGORY, 0)
        }
        viewModel.init()
        init()
        moviesListView.layoutManager = GridLayoutManager(view.context, 2)
        moviesListView.addItemDecoration(
            RecyclerItemDecoration(spacingBottom = 50, isMovieList = true)
        )
        categoriesListView.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
        moviesRefreshSwipeView?.setOnRefreshListener {
            errorHandlerView.visibility = View.INVISIBLE
            viewModel.updateMoviesListByCategory(category)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CATEGORY, category)
        Log.d("list", "saved")
    }

    override fun onDetach() {
        super.onDetach()
        moviesListFragmentClickListener = null
        Log.d("list", "detached")
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
        moviesListView.scrollToPosition(0)
        errorHandlerView.visibility = View.INVISIBLE
        category = categoryId
        viewModel.updateMoviesListByCategory(categoryId)

    }

    private fun onGetDataFailure(message: String?) {
        moviesRefreshSwipeView?.isRefreshing = false
        if (moviesList == null) {
            errorHandlerView.visibility = View.VISIBLE
        }
        Toast.makeText(view?.context, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun setNewMoviesList(await: List<MovieUi>) {
        moviesList = await
        emptyListView?.visibility = if (await.isEmpty()) View.VISIBLE else View.GONE
        moviesAdapter?.updateMoviesList(await)
        moviesListView.scrollToPosition(0) // scroll position loss on restore :C
    }

    private fun setNewCategoriesList(await: List<CategoryUi>) {
        categoriesAdapter?.updateCategoriesList(await)
        categoriesListView.scrollToPosition(0)
    }

    private fun onLoading(loadingState: LoadingState?) {
        when (loadingState?.status) {
            LoadingState.Status.FAILED -> onGetDataFailure(loadingState.msg)
            LoadingState.Status.RUNNING -> moviesRefreshSwipeView?.isRefreshing = true
            LoadingState.Status.SUCCESS -> moviesRefreshSwipeView?.isRefreshing = false
        }
    }

        companion object {
        const val CATEGORY = "category_key"
    }
}
