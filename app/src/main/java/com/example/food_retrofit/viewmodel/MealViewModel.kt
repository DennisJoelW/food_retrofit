package com.example.food_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_retrofit.pojo.Meal
import com.example.food_retrofit.pojo.MealList
import com.example.food_retrofit.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel():ViewModel() {
    private var MealDetailsLIveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, res: Response<MealList>) {
                if(res.body() != null){
                    MealDetailsLIveData.value = res.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(p0: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity", t.message.toString())
            }
        })
    }

    fun obeserveMealDetailLiveData(): LiveData<Meal>{
        return MealDetailsLIveData
    }
}