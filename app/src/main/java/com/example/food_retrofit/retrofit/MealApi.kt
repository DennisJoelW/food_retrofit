package com.example.food_retrofit.retrofit

import com.example.food_retrofit.pojo.CategoryItemsList
import com.example.food_retrofit.pojo.FoodCategory
import com.example.food_retrofit.pojo.CategoryList
import com.example.food_retrofit.pojo.FoodCategoryList
import com.example.food_retrofit.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String) : Call<MealList> // this call<MealList> is what it supposed to return

    @GET("filter.php?")
    fun getPopularItems(@Query("c") id:String) : Call<CategoryList>

    @GET("categories.php")
    fun getFoodCategories():Call<FoodCategoryList>

    @GET("filter.php?")
    fun getCategoryItems(@Query("i") id: String) : Call<CategoryItemsList>
}