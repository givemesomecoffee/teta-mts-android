package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.MovieDetailsViewModel
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop

class MovieDetailsFragment : Fragment() {
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private var ageSign: TextView? = null
    private var movieDetailsHolder: View? = null
    private var ratingBar: RatingBar? = null
    private lateinit var movieCover: ImageView
    private lateinit var errorHandlerView: TextView
    private var movie: MovieUi? = null
    private var refreshWrapper: SwipeRefreshLayout? = null
    private var movieId: Int? = null
    private val viewModel: MovieDetailsViewModel by viewModels()

    private fun init() {
        movieCover = requireView().findViewById(R.id.movie_cover)
        categoryTitle = requireView().findViewById(R.id.movie_category)
        movieTitle = requireView().findViewById(R.id.movie_title)
        movieDescription = requireView().findViewById(R.id.movie_description)
        ageSign = requireView().findViewById(R.id.age_sign)
        refreshWrapper = requireView().findViewById(R.id.swipe_container)
        errorHandlerView = requireView().findViewById(R.id.error_handler)
        movieDetailsHolder = requireView().findViewById(R.id.movie_details_scroll)
        ratingBar = requireView().findViewById(R.id.ratingBar)
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindData))
        viewModel.loadingState.observe(viewLifecycleOwner, Observer(::onLoading))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        movieDetailsHolder?.visibility = View.INVISIBLE
        movieId = arguments?.getInt("id")
        refreshWrapper?.setOnRefreshListener { viewModel.getMovie(movieId) }
        viewModel.getMovie(movieId, true)
    }

    private fun setImgToView(result: Drawable) {
        movieCover.setImageDrawable(result)
        setTopCrop(movieCover)
    }

    private fun bindData(movie: MovieUi) {
        this.movie = movie
        errorHandlerView.visibility = View.INVISIBLE
        categoryTitle?.text = movie.category
        movieTitle?.text = movie.title
        movieDescription?.text = movie.description
        ageSign?.text = movie.ageRestriction
        ratingBar?.rating = movie.rateScore
        movieDetailsHolder?.visibility = View.VISIBLE
        val movieCoverImg = ImageRequest.Builder(requireView().context)
            .data(movie.imageUrl)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .target(onSuccess = { result -> setImgToView(result) })
            .build()
        requireView().context.imageLoader.enqueue(movieCoverImg)
    }

    private fun onGetDataFailure(message: String?) {
        refreshWrapper?.isRefreshing = false
        Toast.makeText(view?.context, message, Toast.LENGTH_SHORT).show()
        if (movie == null) {
            errorHandlerView.visibility = View.VISIBLE
        }
    }

    private fun onLoading(loadingState: LoadingState?) {
        when (loadingState?.status) {
            LoadingState.Status.FAILED -> onGetDataFailure(loadingState.msg)
            LoadingState.Status.RUNNING -> refreshWrapper?.isRefreshing = true
            LoadingState.Status.SUCCESS -> refreshWrapper?.isRefreshing = false
        }
    }
}

