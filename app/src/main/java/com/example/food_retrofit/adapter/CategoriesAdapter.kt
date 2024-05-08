package com.example.food_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_retrofit.databinding.CategoryItemBinding
import com.example.food_retrofit.pojo.CategoryMeals
import com.example.food_retrofit.pojo.FoodCategory
import com.example.food_retrofit.pojo.FoodCategoryList

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    private var categoryList = ArrayList<FoodCategory>()

    fun setCategory(categoryList: ArrayList<FoodCategory>){
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }


    class ViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvCategoryName.setText(categoryList[position].strCategory)

        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

    }

}