package com.example.insurismoviesproject

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.insurismoviesproject.adapter.MovieProductionAdapter
import com.example.insurismoviesproject.retrofit.ApiInterface
import com.example.insurismoviesproject.retrofit.AppClient
import com.example.insurismoviesproject.retrofit.URLHelper
import com.example.insurismoviesproject.retrofit.responces.MoviesDetailsResponse
import com.example.insurismoviesproject.retrofit.responces.ProductionCompaniesItem
import com.example.insurismoviesproject.utill.AppUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private val appClient = AppClient().getClient(this@MovieDetailActivity)
    private val apiInterface = appClient.create(ApiInterface::class.java)
    private var dialog: ProgressDialog? = null

    var movieID:String? = ""

    var movieProductionAdapter: MovieProductionAdapter?=null
    var mProductionList: ArrayList<ProductionCompaniesItem>?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val extras = intent.extras
        movieID = if (extras == null) {
            ""
        } else {
            extras.getString("movie_id", "")
        }

        getMovieDetails()
    }

    private fun showDialog() {
        if (dialog == null) {
            dialog = AppUtil.createProgressDialog(this@MovieDetailActivity)
            dialog!!.show()
        } else {
            dialog!!.show()
        }
    }

    private fun hideDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    @SuppressLint("CheckResult")
    fun getMovieDetails(){
        try {
            showDialog()
            val observable = apiInterface.popularMoviesDetails(
                movie_id= movieID.toString(),
                api_key=getString(R.string.api_key))
                observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableObserver<MoviesDetailsResponse>(){
                        override fun onNext(t: MoviesDetailsResponse) {
                            hideDialog()
                            movieTitle.text = t.title
                            movieTagline.text = t.tagline
                            movieReleaseDate.text = "Release Date: "+t.releaseDate
                            movieRuntime.text = "Runtime: "+t.runtime+" min"

                            Glide.with(applicationContext)
                                .load(URLHelper.IMAGE_BASE_URL+t.posterPath)
                                .into(moviePoster)

                            movieOverview.text = t.overview
                            movieLink.text = t.homepage

                            mProductionList = t.productionCompanies
                            movieProductionAdapter = mProductionList?.let {
                                MovieProductionAdapter(this@MovieDetailActivity,
                                    it
                                )
                            }
                            productionCompaniesList.adapter = movieProductionAdapter


                        }

                        override fun onError(e: Throwable) {
                            hideDialog()
                            e.printStackTrace()
                        }

                        override fun onComplete() {

                        }

                    })
        }catch (e:Exception){
            hideDialog()
            e.printStackTrace()
        }
    }
}