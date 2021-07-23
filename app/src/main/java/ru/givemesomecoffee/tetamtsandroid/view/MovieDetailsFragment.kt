package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.imageLoader
import coil.request.ImageRequest
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.presenter.MovieDetailsPresenter
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop

class MovieDetailsFragment : Fragment() {

    var movieDetailsClickListener: MovieDetailsClickListener? = null
    lateinit var movie: MovieDto
    lateinit var category: CategoryDto
    private var backButton: ImageView? = null
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private var movieCover: ImageView? = null
    private var ageSign: TextView? = null
    private var movieDetailsPresenter = MovieDetailsPresenter(this)
    var refresh: SwipeRefreshLayout? = null


    private fun init() {
        movieCover = view?.findViewById(R.id.movie_cover)
        backButton = view?.findViewById(R.id.back_button)
        categoryTitle = view?.findViewById(R.id.movie_category)
        movieTitle = view?.findViewById(R.id.movie_title)
        movieDescription = view?.findViewById(R.id.movie_description)
        ageSign = view?.findViewById(R.id.age_sign)
        refresh = view?.findViewById(R.id.swipe_container)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsClickListener) {
            movieDetailsClickListener = context
        }
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    movieDetailsClickListener?.moviesDetailsOnBackPressed()
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
        init()
        val id = arguments?.getInt("movie_id")
        movieDetailsPresenter.getData(id)
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_container).setOnRefreshListener {
            movieDetailsPresenter.getData(id)
        }
        backButton?.setOnClickListener {
            movieDetailsClickListener?.moviesDetailsOnBackPressed()

        }


    }

    override fun onDetach() {
        super.onDetach()
        movieDetailsClickListener = null
    }

    private fun setImgToView(result: Drawable) {
        movieCover?.setImageDrawable(result)
        setTopCrop(movieCover!!)
    }




    fun bindData() {
        val ageRestriction = movie.ageRestriction.toString() + "+"
        categoryTitle?.text = category.title
        movieTitle?.text = movie.title
        movieDescription?.text = movie.description
        ageSign?.text = ageRestriction
        val movieCoverImg = ImageRequest.Builder(requireView().context)
            .data(movie.imageUrl)
            .target(onSuccess = { result -> setImgToView(result) })
            .build()
        requireView().context.imageLoader.enqueue(movieCoverImg)
    }

    fun onGetDataFailure(message: String?) {
        Toast.makeText(view?.context, message, Toast.LENGTH_SHORT).show()
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


