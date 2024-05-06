package com.example.food_retrofit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.food_retrofit.databinding.ActivityMealBinding
import com.example.food_retrofit.databinding.FragmentHomeBinding
import com.example.food_retrofit.fragments.HomeFragment
import com.example.food_retrofit.pojo.Meal
import com.example.food_retrofit.viewmodel.MealViewModel
import retrofit2.http.Url
import java.net.URL
import java.sql.Types.NULL

class MealActivity : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealArea:String
    private lateinit var mealCategory:String
    private lateinit var ingredients:ArrayList<String>
    private lateinit var mealInstruction:String
    private lateinit var youtubeLink:String

    private lateinit var mealMvvm: MealViewModel
    private lateinit var adapter: ArrayAdapter<String>



    var instructionOpen: Boolean = false;



    private lateinit var binding: ActivityMealBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        intentMealinfo() // mengambil data dri variabel dari intent ke variable sini

//        setMealInfo()

        openInstruction() // function untuk membuka tutup instruction

        youtubeClick() // function untuk membuka link youtube

        loadingCase()
        mealMvvm.getMealDetail(mealId) // function untuk mengambil detail meal menggunakan mealid
        observerMealDetailLiveData() // observer live data meal kalo ada yg berubah

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        binding.listIngredients.adapter = adapter

        adapter.notifyDataSetChanged()


    }

    private fun youtubeClick() {
        binding.btnYoutube.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailLiveData() {
        mealMvvm.obeserveMealDetailLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                Glide.with(applicationContext)
                    .load(meal.strMealThumb)
                    .into(binding.imgMealDetail)

                binding.txtMealName.setText(meal.strMeal)
                binding.txtMealArea.setText("Origin : "+meal.strArea)
                binding.txtMealCategory.setText("Category : "+meal.strCategory)
                binding.txtInstruction.setText(meal.strInstructions)

                youtubeLink = meal.strYoutube

                ingredients.clear()

                val strIngredients = arrayListOf<String>(
                    meal.strIngredient1, meal.strIngredient2, meal.strIngredient3,
                    meal.strIngredient4, meal.strIngredient5, meal.strIngredient6,
                    meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
                    meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,
                    meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
                    meal.strIngredient16, meal.strIngredient17, meal.strIngredient18,
                    meal.strIngredient19, meal.strIngredient20
                )


                for (ingredient in strIngredients) {
                    if (ingredient != null && ingredient.isNotEmpty()) {
                        ingredients.add(ingredient)
                    }
                }

                updateAdapterData()



            }
        })

    }

    private fun openInstruction() {

            binding.cardInstruction.setOnClickListener() {
                if(!instructionOpen){
                    binding.txtInstruction.visibility = View.VISIBLE
                    instructionOpen = true;
                }
                else if(instructionOpen){
                    binding.txtInstruction.visibility = View.GONE
                    instructionOpen = false;
                }

            }
    }

    private fun updateAdapterData() {
        // Update adapter data with the populated ingredients list
        adapter.clear() // Clear existing adapter data
        adapter.addAll(ingredients) // Add all ingredients to the adapter
        adapter.notifyDataSetChanged() // Notify adapter of data changes
    }


//    private fun setMealInfo() {
//        Glide.with(applicationContext)
//            .load(mealThumb)
//            .into(binding.imgMealDetail)
//
//        binding.txtMealName.setText(mealName)
//        binding.txtMealArea.setText("Origin : "+mealArea)
//        binding.txtMealCategory.setText("Category : "+mealCategory)
//        binding.txtInstruction.setText(mealInstruction)
//    }

    private fun intentMealinfo() {
        val intent = intent;
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID) ?: "NO ID" // null safety
        ingredients = intent.getStringArrayListExtra(HomeFragment.INGREDIENT_LIST)!!
//        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!! // not null (could throw an exception)
//        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
//        mealArea = intent.getStringExtra(HomeFragment.MEAL_AREA)!!
//        mealCategory = intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!
//        mealInstruction = intent.getStringExtra(HomeFragment.MEAL_INSTRUCTION)!!
    }

    private fun loadingCase(){
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.txtMealArea.visibility = View.INVISIBLE
        binding.txtMealName.visibility = View.INVISIBLE
        binding.btnYoutube.visibility = View.INVISIBLE
        binding.divider.visibility = View.INVISIBLE

        binding.progressBar.visibility = View.VISIBLE

    }

    private fun onResponseCase(){

        binding.btnAddToFav.visibility = View.VISIBLE
        binding.txtMealArea.visibility = View.VISIBLE
        binding.txtMealName.visibility = View.VISIBLE
        binding.btnYoutube.visibility = View.VISIBLE
        binding.divider.visibility = View.VISIBLE

        binding.progressBar.visibility = View.INVISIBLE

    }
}