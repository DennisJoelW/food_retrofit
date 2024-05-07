package com.example.food_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_retrofit.fragments.HomeFragment
import com.example.food_retrofit.pojo.CategoryList
import com.example.food_retrofit.pojo.CategoryMeals
import com.example.food_retrofit.pojo.FoodCategory
import com.example.food_retrofit.pojo.FoodCategoryList
import com.example.food_retrofit.pojo.Meal
import com.example.food_retrofit.pojo.MealList
import com.example.food_retrofit.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var ingredients = ArrayList<String>()
    private var ingredientsLiveData = MutableLiveData<ArrayList<String>>()

    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()

    private var foodCategoryLiveData = MutableLiveData<List<FoodCategory>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    ingredients.clear()

                    val strIngredients = arrayListOf<String>(
                        randomMeal.strIngredient1, randomMeal.strIngredient2, randomMeal.strIngredient3,
                        randomMeal.strIngredient4, randomMeal.strIngredient5, randomMeal.strIngredient6,
                        randomMeal.strIngredient7, randomMeal.strIngredient8, randomMeal.strIngredient9,
                        randomMeal.strIngredient10, randomMeal.strIngredient11, randomMeal.strIngredient12,
                        randomMeal.strIngredient13, randomMeal.strIngredient14, randomMeal.strIngredient15,
                        randomMeal.strIngredient16, randomMeal.strIngredient17, randomMeal.strIngredient18,
                        randomMeal.strIngredient19, randomMeal.strIngredient20
                    )


                    for (ingredient in strIngredients) {
                        if (ingredient != null && ingredient.isNotEmpty()) {
                            ingredients.add(ingredient)
                        }
                    }

                    ingredientsLiveData.value = ingredients

                }else{

                }
            }
            override fun onFailure(p0: Call<MealList>, t: Throwable) {
                Log.d("API GET Failure", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object: Callback<CategoryList> {
            override fun onResponse(p0: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.d("HomeFragment", p1.message.toString())
            }

        })
    }

    fun getFoodCategories(){
        RetrofitInstance.api.getFoodCategories().enqueue(object: Callback<FoodCategoryList>{
            override fun onResponse(p0: Call<FoodCategoryList>, response: Response<FoodCategoryList>) {
                if(response.body() != null){
                    response.body()?.let { FoodCategoryList ->
                        foodCategoryLiveData.postValue(FoodCategoryList.categories)
                    }
                }
            }

            override fun onFailure(p0: Call<FoodCategoryList>, p1: Throwable) {
                Log.d("HomeViewModel", p1.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> { //liveData can only read
        return randomMealLiveData;
    }
    fun observeIngredientsLiveData(): LiveData<ArrayList<String>> {
        return ingredientsLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<CategoryMeals>>{
        return popularItemsLiveData;
    }

    fun observeFoodCategoryLiveData(): LiveData<List<FoodCategory>>{
        return foodCategoryLiveData;
    }


}