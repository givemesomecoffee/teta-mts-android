package ru.givemesomecoffee.tetamtsandroid

import android.content.Context
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
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies

class MovieDetailsFragment : Fragment() {
    private var someFragmentClickListener: MovieDetailsClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        val id = arguments?.getInt("movie_id")
        val movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)

        val backButton = view.findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { onPause() }

        val imageView = view.findViewById<ImageView>(R.id.movie_cover)

        val request = ImageRequest.Builder(view.context)
            .data(movie.imageUrl)
            .target(
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                    val matrix = imageView.imageMatrix;
                    val imageWidth = imageView.drawable.intrinsicWidth;
                    val screenWidth = resources.displayMetrics.widthPixels;
                    val scaleRatio = screenWidth / imageWidth.toFloat();
                    matrix.postScale(scaleRatio, scaleRatio);
                    imageView.imageMatrix = matrix;

                }
            )
            .build()
        view.context.imageLoader.enqueue(request)

        view.apply {
            val category =
                Categories(MovieCategoriesDataSourceImpl()).getCategoryById(movie.categoryId)
            findViewById<TextView>(R.id.movie_category).text =
                category.title
            findViewById<TextView>(R.id.movie_name).text = movie.title //TODO: rename
            findViewById<TextView>(R.id.movie_description).text = movie.description
            val ageRestriction = movie.ageRestriction.toString() + "+"
            findViewById<TextView>(R.id.age_sign).text = ageRestriction
        }

        return view
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

    interface MovieDetailsClickListener {
        fun onBackStack()
    }

    override fun onPause() {
        someFragmentClickListener?.onBackStack()
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsClickListener) {
            someFragmentClickListener = context
        }

        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onPause()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }


    override fun onDetach() {
        super.onDetach()
        someFragmentClickListener = null
    }

}