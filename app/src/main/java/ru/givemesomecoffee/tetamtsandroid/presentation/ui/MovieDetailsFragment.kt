package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.CustomDateAdapter
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.MovieDetailsViewModel
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.ActorsAdapter
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.RecyclerItemDecoration
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.setTopCrop
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MovieDetailsFragment : Fragment() {
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private var ageSign: TextView? = null
    private var movieDetailsHolder: View? = null
    private var ratingBar: RatingBar? = null
    private var releaseDateView: TextView? = null
    private lateinit var movieCover: ImageView
    private lateinit var errorHandlerView: TextView
    private var movie: MovieUi? = null
    private var refreshWrapper: SwipeRefreshLayout? = null
    private var movieId: Int? = null
    private var progressBarCover: ProgressBar? = null
    private var actorsListView: RecyclerView? = null

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
        releaseDateView = requireView().findViewById(R.id.movie_date)
        progressBarCover = requireView().findViewById(R.id.movie_cover_progress_bar)
        actorsListView = requireView().findViewById(R.id.actors_section)
        actorsListView!!.addItemDecoration(RecyclerItemDecoration(10, 0, 20))
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
        viewModel.init(movieId)
        refreshWrapper?.setOnRefreshListener { viewModel.getMovie(movieId) }
    }

    private fun setImgToView(result: Drawable) {
        Log.d("coil", "i sucsess")
        progressBarCover?.visibility = View.INVISIBLE
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
        if (movie.ageRestriction.isNullOrEmpty()) {
            ageSign?.visibility = View.INVISIBLE
        } else {
            ageSign?.visibility = View.VISIBLE
        }
        if (movie.releaseDate != null) {
            releaseDateView?.text = SimpleDateFormat("dd.MM.yyyy").format(movie.releaseDate)
        }
        ratingBar?.rating = movie.rateScore
        movieDetailsHolder?.visibility = View.VISIBLE
        val movieCoverImg = ImageRequest.Builder(requireView().context)
            .error(R.drawable.no_image)
            .data(movie.imageUrl)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .target(onSuccess = { result -> setImgToView(result) },
                onError = { result -> setImgToView(result!!) },
                onStart = { progressBarCover?.visibility = View.VISIBLE })
            .build()
        requireView().context.imageLoader.enqueue(movieCoverImg)
        if (movie.actors != null) {
            actorsListView?.adapter = ActorsAdapter(movie.actors)
        }
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

