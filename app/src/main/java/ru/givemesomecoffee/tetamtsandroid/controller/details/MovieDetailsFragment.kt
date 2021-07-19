package ru.givemesomecoffee.tetamtsandroid.controller.details

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
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop

class MovieDetailsFragment : Fragment() {
    private var movieDetailsClickListener: MovieDetailsClickListener? = null

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

    interface MovieDetailsClickListener {
        fun customOnBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("movie_id")
        val movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)
        view.bind(movie)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsClickListener) {
            movieDetailsClickListener = context
        }

        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    movieDetailsClickListener?.customOnBackPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        movieDetailsClickListener = null
    }

    private fun View.bind(movie: MovieDto) {
        val ageRestriction = movie.ageRestriction.toString() + "+"
        val movieCover = findViewById<ImageView>(R.id.movie_cover)
        val category =
            Categories(MovieCategoriesDataSourceImpl()).getCategoryById(movie.categoryId)
        val movieCoverImg = ImageRequest.Builder(context)
            .data(movie.imageUrl)
            .target(onSuccess = { result -> setImgToView(movieCover, result) })
            .build()
        context.imageLoader.enqueue(movieCoverImg)

        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            movieDetailsClickListener?.customOnBackPressed()
        }
        findViewById<TextView>(R.id.movie_category).text = category.title
        findViewById<TextView>(R.id.movie_title).text = movie.title
        findViewById<TextView>(R.id.movie_description).text = movie.description
        findViewById<TextView>(R.id.age_sign).text = ageRestriction
    }

    private fun setImgToView(movieCover: ImageView, result: Drawable) {
        movieCover.setImageDrawable(result)
        setTopCrop(movieCover)
    }

}


