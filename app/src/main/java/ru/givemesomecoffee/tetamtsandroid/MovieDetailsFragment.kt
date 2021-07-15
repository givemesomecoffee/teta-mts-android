package ru.givemesomecoffee.tetamtsandroid

import android.graphics.drawable.Drawable
import android.opengl.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.imageLoader
import coil.load
import coil.metadata
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Movies

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        val id = arguments?.getInt("movie_id")
        val movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)


        val imageView = view.findViewById<ImageView>(R.id.movie_cover)
   /*     imageView.load(movie.imageUrl){
            scale(Scale.FILL) //TODO: не работает
                .target { drawable ->
        }*/


            val request = ImageRequest.Builder(view.context)
                .data(movie.imageUrl)
                .target(
                    onStart = { placeholder ->
                        // Handle the placeholder drawable.
                    },
                    onSuccess = { result ->
                   /*     val viewWidth: Float = imageView.width.toFloat()
                        val viewHeight: Float = imageView.height.toFloat()
                        val drawableWidth = result.intrinsicWidth
                        val drawableHeight = result.intrinsicHeight

                        val widthScale = viewWidth / drawableWidth
                        val heightScale = viewHeight / drawableHeight
                        val scale = widthScale.coerceAtLeast(heightScale)*/
                        imageView.setImageDrawable(result)
            /*            imageView.imageMatrix.postScale(scale, scale)*/

                        val matrix = imageView.imageMatrix;
                        val imageWidth = imageView.getDrawable().getIntrinsicWidth();
                       val screenWidth = getResources().getDisplayMetrics().widthPixels;
                        val scaleRatio = screenWidth / imageWidth.toFloat();
                        matrix.postScale(scaleRatio, scaleRatio);
                        imageView.setImageMatrix(matrix);

                    },
                    onError = { error ->
                        // Handle the error drawable.
                    }
                )
                .build()
            view.context.imageLoader.enqueue(request)








        view.apply {

            findViewById<TextView>(R.id.movie_category).text =
                movie.categoryId.toString() //TODO: categories
            findViewById<TextView>(R.id.movie_name).text = movie.title //TODO: rename
            findViewById<TextView>(R.id.movie_description).text = movie.description
            val ageRestriction = movie.ageRestriction.toString() + "1"
            findViewById<TextView>(R.id.age_sign).text = ageRestriction
        }


        return view
    }
suspend fun getCover(view: View, url: String): Drawable{
    val request = ImageRequest.Builder(view.context)
        .data("https://www.example.com/image.jpg")
        .allowHardware(false) // Disable hardware bitmaps.
        .build()
    return (view.context.imageLoader.execute(request) as SuccessResult).drawable
}

    companion object {
        fun newInstance(id: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt("movie_id", id)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}