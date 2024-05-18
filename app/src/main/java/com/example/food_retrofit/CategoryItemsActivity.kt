package com.example.food_retrofit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.food_retrofit.adapter.CategoriesAdapter
import com.example.food_retrofit.databinding.ActivityCategoryItemsBinding
import com.example.food_retrofit.viewmodel.CategoryVIewModel

class CategoryItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryItemsBinding
    private lateinit var categoryMvvm:CategoryVIewModel

    private lateinit var catName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMvvm = ViewModelProvider(this).get(CategoryVIewModel::class.java)

        getIntentInfo()

        binding.txtWhatCategory.setText(catName)

    }

    private fun getIntentInfo() {
        val intent = intent;
        catName = intent.getStringExtra(CategoriesAdapter.categoryName) ?: "NO CATEGORY"
    }
}