package com.example.insurismoviesproject

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.insurismoviesproject.adapter.PopularMoviesAdapter
import com.example.insurismoviesproject.retrofit.ApiInterface
import com.example.insurismoviesproject.retrofit.AppClient
import com.example.insurismoviesproject.retrofit.responces.MoviesListResultsItem
import com.example.insurismoviesproject.retrofit.responces.PopularMoviesListResponse
import com.example.insurismoviesproject.utill.AppUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val appClient = AppClient().getClient(this@MainActivity)
    private val apiInterface = appClient.create(ApiInterface::class.java)
    private var dialog: ProgressDialog? = null

    var popularMoviesAdapter: PopularMoviesAdapter?= null
    var moviesList: ArrayList<MoviesListResultsItem>?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchMoviesList()
    }

    private fun showDialog() {
        if (dialog == null) {
            dialog = AppUtil.createProgressDialog(this@MainActivity)
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
    private fun fetchMoviesList(){
        try {
            showDialog()
            val observable = apiInterface.popularMovies(getString(R.string.api_key))
            observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<PopularMoviesListResponse>(){
                    override fun onNext(t: PopularMoviesListResponse) {
                        hideDialog()
                        if (t.results != null){
                            moviesList = t.results
                            popularMoviesAdapter = PopularMoviesAdapter(this@MainActivity,
                                moviesList!!
                            )
                            favMoviesRV.adapter = popularMoviesAdapter
                        }
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