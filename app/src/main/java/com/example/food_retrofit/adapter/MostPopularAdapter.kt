package com.example.food_retrofit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_retrofit.databinding.PopularItemsBinding
import com.example.food_retrofit.pojo.CategoryMeals

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    private var mealsList = ArrayList<CategoryMeals>()

    lateinit var onitemClick:((CategoryMeals) -> Unit)

    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) { //mandatory methods (holder yang bisa ngatur viewnya)

        holder.binding.parent.setOnClickListener(){
            onitemClick.invoke(mealsList[position])
        }
        holder.binding.tvPopularMealName.setText(mealsList[position].strMeal)

        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)


    }

    override fun getItemCount(): Int {
        return mealsList.size
    }



}
