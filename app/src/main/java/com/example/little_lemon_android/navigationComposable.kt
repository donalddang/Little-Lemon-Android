package com.example.little_lemon_android

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation(
    sharedPreferences: SharedPreferences,
    items: List<MenuItemDatabase>,
    filterByCategory:(String) -> Unit,
    filterByQuery: (String) -> Unit
){
    val firstName = sharedPreferences.getString("firstName", null)
    val lastName = sharedPreferences.getString("lastName", null)
    val email = sharedPreferences.getString("email", null)
    val onboardingRequired = firstName == null || lastName == null || email == null
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if (onboardingRequired) Onboarding.route else Home.route){//generic false value for now, to be changed later
        composable(Onboarding.route){
            Onboarding(navController, sharedPreferences)
        }
        composable(Home.route){
            Home(navController, sharedPreferences,items,  filterByCategory, filterByQuery)
        }
        composable(Profile.route){
            Profile(navController, sharedPreferences)
        }
    }
}