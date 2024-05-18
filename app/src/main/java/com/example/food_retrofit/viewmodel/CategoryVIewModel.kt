package com.example.food_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_retrofit.pojo.CategoryItems
import com.example.food_retrofit.pojo.CategoryItemsList
import com.example.food_retrofit.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryVIewModel():ViewModel() {
    private var categoryItemsLiveData = MutableLiveData<List<CategoryItems>>()

    fun getCategoryItems(selectedCategory: String){
        RetrofitInstance.api.getCategoryItems(selectedCategory).enqueue(object:
            Callback<CategoryItemsList> {
            override fun onResponse(p0: Call<CategoryItemsList>, response: Response<CategoryItemsList>) {
                if(response.body() != null){
                    response.body()?.let { CategoryItemsList ->
                        categoryItemsLiveData.postValue(CategoryItemsList.meals)
                    }
                }
            }

            override fun onFailure(p0: Call<CategoryItemsList>, p1: Throwable) {
                Log.d("HomeViewModel", p1.message.toString())
            }

        })
    }

    fun observeCategoryItemsLiveData(): LiveData<List<CategoryItems>> {
        return categoryItemsLiveData;
    }
}