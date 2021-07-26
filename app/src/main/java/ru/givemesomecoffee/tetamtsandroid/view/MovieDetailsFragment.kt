package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.presenter.MovieDetailsPresenter
import ru.givemesomecoffee.tetamtsandroid.presenter.State
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop

class MovieDetailsFragment : Fragment() {
    var movieDetailsClickListener: MovieDetailsClickListener? = null
    private var backButton: ImageView? = null
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private lateinit var movieCover: ImageView
    private var ageSign: TextView? = null
    private var movieDetailsPresenter = MovieDetailsPresenter(this)
    var refreshWrapper: SwipeRefreshLayout? = null
    var movieId: Int? = null
    private lateinit var errorHandlerView: TextView
    private var movie: MovieDto? = null

    private fun init() {
        movieCover = requireView().findViewById(R.id.movie_cover)
        categoryTitle = view?.findViewById(R.id.movie_category)
        movieTitle = view?.findViewById(R.id.movie_title)
        movieDescription = view?.findViewById(R.id.movie_description)
        ageSign = view?.findViewById(R.id.age_sign)
        refreshWrapper = view?.findViewById(R.id.swipe_container)
        errorHandlerView = requireView().findViewById(R.id.error_handler)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsClickListener) {
            movieDetailsClickListener = context
        }
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    movieDetailsClickListener?.movieDetailsOnBackPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.movie_details_scroll)?.visibility = View.INVISIBLE
        super.onViewCreated(view, savedInstanceState)
        init()
        movieId = arguments?.getInt("movie_id")
        refreshWrapper?.setOnRefreshListener { collectData(movieId) }
        backButton?.setOnClickListener {
            movieDetailsClickListener?.movieDetailsOnBackPressed()
        }
        collectData(movieId)
    }

    override fun onDetach() {
        super.onDetach()
        movieDetailsClickListener = null
    }

    private fun collectData(id: Int?) {
        viewLifecycleOwner.lifecycleScope.launch() {
            movieDetailsPresenter.getMovie(id)
                .catch { e -> onGetDataFailure(e.message) }
                .collect {
                    when (it) {
                        is State.DataState<*> -> bindData(it.data as MovieDto)
                        is State.LoadingState -> refreshWrapper?.isRefreshing = true
                    }
                }
        }
        refreshWrapper?.isRefreshing = false
    }

    private fun setImgToView(result: Drawable) {
        movieCover.setImageDrawable(result)
        setTopCrop(movieCover)
    }

    private fun bindData(movie: MovieDto) {
        this.movie = movie
        errorHandlerView.visibility = View.INVISIBLE
        view?.findViewById<View>(R.id.group)?.visibility = View.VISIBLE
        view?.findViewById<View>(R.id.movie_details_scroll)?.visibility = View.VISIBLE
        categoryTitle?.text = movie.categoryTitle
        movieTitle?.text = movie.title
        movieDescription?.text = movie.description
        ageSign?.text = movie.ageRestrictionText
        view?.findViewById<RatingBar>(R.id.ratingBar)?.rating = movie.rateScore.toFloat()
        val movieCoverImg = ImageRequest.Builder(requireView().context)
            .data(movie.imageUrl)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .target(onSuccess = { result -> setImgToView(result) })
            .build()
        requireView().context.imageLoader.enqueue(movieCoverImg)
    }

    private fun onGetDataFailure(message: String?) {
        Toast.makeText(view?.context, message, Toast.LENGTH_SHORT).show()
        if (movie == null) {
            errorHandlerView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val MOVIE_DETAILS_TAG = "MovieDetails"
        fun newInstance(id: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt("movie_id", id)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


