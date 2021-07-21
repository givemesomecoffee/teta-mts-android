package ru.givemesomecoffee.tetamtsandroid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.view.viewholder.MovieDetailsViewHolder

class MovieDetailsFragment : Fragment() {
    private var movieDetailsClickListener: MovieDetailsClickListener? = null
    private lateinit var movie: MovieDto
    private lateinit var viewHolder: MovieDetailsViewHolder

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
        fun hideNavigation()
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
        movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)
        viewHolder = MovieDetailsViewHolder(view, movieDetailsClickListener)
        viewHolder.bind(movie)
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

    override fun onStart() {
        super.onStart()
        movieDetailsClickListener?.hideNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        movieDetailsClickListener = null
    }
}


