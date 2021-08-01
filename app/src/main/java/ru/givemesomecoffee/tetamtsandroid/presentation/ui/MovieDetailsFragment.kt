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
        categoryTitle = view?.findViewById(R.id.movie_category)
        movieTitle = view?.findViewById(R.id.movie_title)
        movieDescription = view?.findViewById(R.id.movie_description)
        ageSign = view?.findViewById(R.id.age_sign)
        refreshWrapper = view?.findViewById(R.id.swipe_container)
        errorHandlerView = requireView().findViewById(R.id.error_handler)
        movieDetailsHolder = requireView().findViewById(R.id.movie_details_scroll)
        ratingBar = requireView().findViewById(R.id.ratingBar)
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindData))
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
        movieDetailsHolder?.visibility = View.INVISIBLE
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getInt("id")
        refreshWrapper?.setOnRefreshListener { viewModel.getMovie(movieId, this) }
        viewModel.getMovie(movieId, this)
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
        refreshWrapper?.isRefreshing = false
    }

    fun onGetDataFailure(message: Throwable) {
        refreshWrapper?.isRefreshing = false
        Toast.makeText(view?.context, "Ошибка. Обновите страницу", Toast.LENGTH_SHORT).show()
        //Log.d("test", message.stackTraceToString())
        if (movie == null) {
            errorHandlerView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val MOVIE_DETAILS_TAG = "MovieDetails"
        fun newInstance(id: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt("id", id)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


