package com.example.food_retrofit.fragments

import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food_retrofit.MealActivity
import com.example.food_retrofit.adapter.CategoriesAdapter
import com.example.food_retrofit.adapter.MostPopularAdapter
import com.example.food_retrofit.databinding.FragmentHomeBinding
import com.example.food_retrofit.pojo.CategoryMeals
import com.example.food_retrofit.pojo.FoodCategory
import com.example.food_retrofit.pojo.Meal
import com.example.food_retrofit.viewmodel.HomeViewModel
import retrofit2.Response



class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var ingredients:List<String>
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{ // this is a class inside a view so that it can be accessed
        const val MEAL_ID = "com.example.food_retrofit.fragments.idMeal"
        const val MEAL_NAME = "com.example.food_retrofit.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.food_retrofit.fragments.thumbMeal"
        const val MEAL_CATEGORY = "com.example.food_retrofit.fragments.category"
        const val MEAL_AREA = "com.example.food_retrofit.fragments.area"
        const val INGREDIENT_LIST = "com.example.food_retrofit.fragments.ingredient"
        const val MEAL_INSTRUCTION = "com.example.food_retrofit.fragments.instruction"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter(requireActivity())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        prepareFoodCategoryRecyclerView()

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        observeIngredientsLiveData()

        onRandomMealClick() // ketika user click image random meal

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClicked()

        homeMvvm.getFoodCategories()
        observeFoodCategoryLiveData()
    }


    private fun onPopularItemClicked() {
        popularItemsAdapter.onitemClick = {meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putStringArrayListExtra(INGREDIENT_LIST, ingredients as ArrayList<String>)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recviewPopular.apply {
            layoutManager = GridLayoutManager(activity, 2,GridLayoutManager.VERTICAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun prepareFoodCategoryRecyclerView() {
        binding.recviewCategory.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun observeFoodCategoryLiveData() {
        homeMvvm.observeFoodCategoryLiveData().observe(viewLifecycleOwner){
            categoryList ->
            categoriesAdapter.setCategory(categoryList = categoryList as ArrayList<FoodCategory>)
        }
    }


    private fun onRandomMealClick() {
        binding.cardRandomFood.setOnClickListener(){
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            intent.putExtra(MEAL_AREA, randomMeal.strArea)
            intent.putExtra(MEAL_CATEGORY, randomMeal.strCategory)
            intent.putStringArrayListExtra(INGREDIENT_LIST, ingredients as ArrayList<String>)
            intent.putExtra(MEAL_INSTRUCTION, randomMeal.strInstructions)
            startActivity(intent)
        }
    }


    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imgRandomFood)

                this.randomMeal = meal
            }
        })
    }

    private fun observeIngredientsLiveData() {
        homeMvvm.observeIngredientsLiveData().observe(viewLifecycleOwner, Observer { ingredient ->
            ingredient?. let {
                this.ingredients = ingredient
            }
        })
    }








}

