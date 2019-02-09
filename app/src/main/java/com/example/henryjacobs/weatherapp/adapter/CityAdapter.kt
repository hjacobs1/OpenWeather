package com.example.henryjacobs.weatherapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.henryjacobs.weatherapp.DetailsActivity
import com.example.henryjacobs.weatherapp.R
import com.example.henryjacobs.weatherapp.MainActivity
import com.example.henryjacobs.weatherapp.data.AppDatabase
import com.example.henryjacobs.weatherapp.data.City
import com.example.henryjacobs.weatherapp.network.WeatherAPI
import kotlinx.android.synthetic.main.city_row.view.*
import android.support.v7.app.AppCompatActivity
import android.util.Log


class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>{
    
    var cityItems = mutableListOf<City>()

    var context: Context
    
    constructor(context: Context, items: List<City>) : super() {
        this.context = context
        this.cityItems.addAll(items)
    }

    constructor(context: Context) : super(){
        this.context = context
    }

    // what one item in the recycler view looks like
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityItems.size
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityItems[position]
        holder.tvCityName.text = city.cityName

        holder.btnRemove.setOnClickListener{
            deleteCity(holder.adapterPosition)
        }

        holder.itemView.setOnClickListener{
            var intentDetails = Intent(context, DetailsActivity::class.java)
            intentDetails.putExtra(context.getString(R.string.key_city), cityItems[holder.adapterPosition].cityName)
            context.startActivity(intentDetails)
        }
    }
    
    private fun deleteCity(adapterPosition: Int) {
        Thread{
            AppDatabase.getInstance(context).cityDao().deleteCity(
                cityItems[adapterPosition]
            )
            cityItems.removeAt(adapterPosition)
            (context as MainActivity).runOnUiThread{
                notifyItemRemoved(adapterPosition)
            }
        }.start()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCityName = itemView.tvCityName
        val btnRemove = itemView.btnRemove
    }

    fun addCity(city: City){
        cityItems.add(0, city)
        notifyItemInserted(0)
    }
}
