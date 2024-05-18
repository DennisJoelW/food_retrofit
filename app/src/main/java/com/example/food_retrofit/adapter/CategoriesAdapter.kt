package com.example.food_retrofit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_retrofit.CategoryItemsActivity
import com.example.food_retrofit.databinding.CategoryItemBinding
import com.example.food_retrofit.databinding.FragmentCategoriesBinding
import com.example.food_retrofit.fragments.CategoriesFragment
import com.example.food_retrofit.fragments.HomeFragment
import com.example.food_retrofit.pojo.FoodCategory

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    private var categoryList = ArrayList<FoodCategory>()


    private lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context
    }

    companion object{
        const val categoryName = "podqwpq";
    }


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

        holder.binding.cardRecCategories.setOnClickListener(){
            val intent = Intent(context, CategoryItemsActivity::class.java)
            intent.putExtra(CategoriesAdapter.categoryName, categoryList[position].strCategory )
            context.startActivity(intent)
        }

    }

}