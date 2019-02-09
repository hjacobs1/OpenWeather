package com.example.henryjacobs.weatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.henryjacobs.weatherapp.data.WeatherResult
import com.example.henryjacobs.weatherapp.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    private val HOST_URL = "https://api.openweathermap.org/"
    private val API_KEY = "724d7ef4297105b3e0e82d05218f3bb5"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // creating the retrofit object
        val retrofit = Retrofit.Builder().
            baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(WeatherAPI::class.java)

            val weatherDetailsCall = weatherAPI.getWeatherDetails(intent.getStringExtra(getString(R.string.key_city)), getString(
                            R.string.units), API_KEY) // creating the call must be in the onClickListener
            weatherDetailsCall.enqueue(object : Callback<WeatherResult> {
                override fun onFailure(call: Call<WeatherResult>, t: Throwable) {  // t will be the exception
                    Toast.makeText(this@DetailsActivity,"Call to API failed: ${t.message}", Toast.LENGTH_LONG).show()
                }


                override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                    val weatherResult = response.body()

                    Glide.with(this@DetailsActivity) .load(
                        (getString(R.string.weather_icon_url) + response.body()?.weather?.get(0)?.icon + getString(R.string.png_extension)))
                        .into(ivWeather)

                    tvCoord.text =
                            "${String.format(getString(R.string.string_format), weatherResult?.coord?.lat?.toFloat())}"
                    tvCoord.append(", ${weatherResult?.coord?.lon?.toFloat()}")

                    tvWeath.text =
                             weatherResult?.weather?.get(0)?.main.toString()

                    tvDesc.text = weatherResult?.weather?.get(0)?.description.toString()

                    tvTemp.text = "${String.format(getString(R.string.string_format), weatherResult?.main?.temp?.toFloat())}"

                    tvPres.text = "${String.format(getString(R.string.string_format), weatherResult?.main?.pressure?.toFloat())}"

                    tvHum.text = "${String.format(getString(R.string.string_format), weatherResult?.main?.humidity?.toFloat())}"

                    tvMinTemp.text = "${String.format(getString(R.string.string_format), weatherResult?.main?.temp_min?.toFloat())}"

                    tvMaxTemp.text = "${String.format(getString(R.string.string_format), weatherResult?.main?.temp_max?.toFloat())}"
                }
            })
    }
}
