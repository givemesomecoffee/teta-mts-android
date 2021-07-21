package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import coil.imageLoader
import coil.request.ImageRequest
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop

class MovieDetailsFragment : Fragment() {

    var movieDetailsClickListener: MovieDetailsClickListener? = null
    private lateinit var movie: MovieDto
    private lateinit var category: CategoryDto
    private var backButton: ImageView? = null
    private var categoryTitle: TextView? = null
    private var movieTitle: TextView? = null
    private var movieDescription: TextView? = null
    private var movieCover: ImageView? = null
    private var ageSign: TextView? = null

    private fun init() {
        val id = arguments?.getInt("movie_id")
        movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)
        category =
            Categories(MovieCategoriesDataSourceImpl()).getCategoryById(movie.categoryId)
        movieCover = view?.findViewById(R.id.movie_cover)
        backButton = view?.findViewById(R.id.back_button)
        categoryTitle = view?.findViewById(R.id.movie_category)
        movieTitle = view?.findViewById(R.id.movie_title)
        movieDescription = view?.findViewById(R.id.movie_description)
        ageSign = view?.findViewById(R.id.age_sign)
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
        val ageRestriction = movie.ageRestriction.toString() + "+"
        backButton?.setOnClickListener {
            movieDetailsClickListener?.moviesDetailsOnBackPressed()
        }
        categoryTitle?.text = category.title
        movieTitle?.text = movie.title
        movieDescription?.text = movie.description
        ageSign?.text = ageRestriction
        val movieCoverImg = ImageRequest.Builder(view.context)
            .data(movie.imageUrl)
            .target(onSuccess = { result -> setImgToView(result) })
            .build()
        view.context.imageLoader.enqueue(movieCoverImg)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        movieDetailsClickListener?.hideNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        movieDetailsClickListener = null
    }

    private fun setImgToView(result: Drawable) {
        movieCover?.setImageDrawable(result)
        setTopCrop(movieCover!!)
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


