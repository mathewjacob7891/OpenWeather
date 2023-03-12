package com.mathew.openweather.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mathew.openweather.R
import com.mathew.openweather.model.City

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    var onItemClickListener: ((City?) -> Unit)? = null
    private var cityList = ArrayList<City>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cityList: List<City>) {
        this.cityList.clear()
        this.cityList.addAll(cityList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.textViewCityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        bindData(city, holder)
    }

    private fun bindData(city: City?, holder: ViewHolder) {
        holder.apply {
            cityName.text = city?.cityName
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(city)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}