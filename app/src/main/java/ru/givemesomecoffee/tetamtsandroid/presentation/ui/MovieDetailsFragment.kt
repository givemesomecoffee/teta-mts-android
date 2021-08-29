package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.TransitionInflater
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.givemesomecoffee.data.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.MovieDetailsViewModel
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.MovieDetailsViewModelFactory
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.ActorsAdapter
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.RecyclerItemDecoration
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.setTopCrop
import java.text.SimpleDateFormat
import javax.inject.Inject

const val MOVIE_ID = "id"
const val MOVIE_URL = "url"

class MovieDetailsFragment : Fragment() {
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private var ageSign: TextView? = null
    private var movieDetailsHolder: View? = null
    private var ratingBar: RatingBar? = null
    private var releaseDateView: TextView? = null
    private lateinit var movieCover: ImageView
    private var movie: MovieUi? = null
    private var refreshWrapper: SwipeRefreshLayout? = null
    private var movieId: Int? = null
    private var progressBarCover: ProgressBar? = null
    private var actorsListView: RecyclerView? = null
    private var isRefreshData: Boolean = false

    private val viewModel: MovieDetailsViewModel by viewModels {
        factory.create(movieId!!)
    }

    @Inject
    lateinit var factory: MovieDetailsViewModelFactory.Factory

    private fun init() {
        postponeEnterTransition()
        movieCover = requireView().findViewById(R.id.movie_cover)
        movieCover.transitionName = requireArguments().getString(MOVIE_URL)
        categoryTitle = requireView().findViewById(R.id.movie_category)
        movieTitle = requireView().findViewById(R.id.movie_title)
        movieDescription = requireView().findViewById(R.id.movie_description)
        ageSign = requireView().findViewById(R.id.age_sign)
        refreshWrapper = requireView().findViewById(R.id.swipe_container)
        movieDetailsHolder = requireView().findViewById(R.id.movie_details_scroll)
        ratingBar = requireView().findViewById(R.id.ratingBar)
        releaseDateView = requireView().findViewById(R.id.movie_date)
        progressBarCover = requireView().findViewById(R.id.movie_cover_progress_bar)
        actorsListView = requireView().findViewById(R.id.actors_section)
        actorsListView!!.addItemDecoration(RecyclerItemDecoration(10, 0, 20))
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindData))
        viewModel.loadingState.observe(viewLifecycleOwner, Observer(::onLoading))
    }

    override fun onAttach(context: Context) {
        movieId = requireArguments().getInt(MOVIE_ID)
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        refreshWrapper?.setOnRefreshListener {
            isRefreshData = true
            viewModel.getMovie()
        }
    }

    private fun loadMovieCover() {
        val key = requireArguments().getString(MOVIE_URL)

        val movieCoverImg = ImageRequest.Builder(requireView().context)
            .error(R.drawable.no_image)
            .placeholderMemoryCacheKey(key)
            .data(key)
            .allowHardware(false)
            .target(
                onSuccess = { result -> setImgToView(result) },
                onError = { result -> setImgToView(result) }
            )
            .build()
        requireView().context.imageLoader.enqueue(movieCoverImg)
    }

    private fun setImgToView(result: Drawable?) {
        requireView().findViewById<ProgressBar>(R.id.movie_cover_progress_bar).visibility =
            View.INVISIBLE
        if (result != null) {
            movieCover.setImageDrawable(result)
        }
        movieCover.scaleType = ImageView.ScaleType.MATRIX
        setTopCrop(movieCover)
        startPostponedEnterTransition()
    }

    private fun bindData(movie: MovieUi) {
        this.movie = movie
        movieTitle?.text = movie.title
        categoryTitle?.text = movie.category
        categoryTitle?.setBackgroundResource(R.drawable.round_border)
        movieDescription?.text = movie.description
        ageSign?.text = movie.ageRestriction
        if (movie.ageRestriction.isNullOrEmpty()) {
            ageSign?.background = null
        } else{
            ageSign?.setBackgroundResource(R.drawable.circle)
        }
        if (movie.releaseDate != null) {
            @Suppress("SimpleDateFormat")
            releaseDateView?.text = SimpleDateFormat("dd.MM.yyyy").format(movie.releaseDate!!)
        }
        ratingBar?.rating = movie.rateScore

            val movieCoverImg = ImageRequest.Builder(requireView().context)
                .error(R.drawable.no_image)
                .placeholderMemoryCacheKey(movie.imageUrl)
                .data(movie.imageUrl)
                .allowHardware(false)
                .target(
                    onSuccess = { result -> setImgToView(result) },
                    onError = { result -> setImgToView(result) }
                )
                .build()
            requireView().context.imageLoader.enqueue(movieCoverImg)
            isRefreshData = false

        if (movie.actors != null) {
            actorsListView?.adapter = ActorsAdapter(movie.actors!!)
        }

    }

    private fun onGetDataFailure(message: String?) {
        refreshWrapper?.isRefreshing = false
        Toast.makeText(view?.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun onLoading(loadingState: LoadingState?) {
        when (loadingState?.status) {
            LoadingState.Status.FAILED -> onGetDataFailure(loadingState.msg)
            LoadingState.Status.RUNNING -> refreshWrapper?.isRefreshing = true
            LoadingState.Status.SUCCESS -> refreshWrapper?.isRefreshing = false
        }
    }
}

