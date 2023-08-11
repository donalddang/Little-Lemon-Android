package com.example.little_lemon_android

import com.example.little_lemon_android.ui.theme.LittleLemonAndroidTheme
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, MenuDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonAndroidTheme {
                val databaseMenuItems by database.menuDao().getAll().observeAsState(emptyList())
                var filteredItems by remember {
                    mutableStateOf(databaseMenuItems)
                }
                val navController = rememberNavController()
                var populateDatabase by remember {
                    mutableStateOf(false)
                }
                if (!populateDatabase && databaseMenuItems.isNotEmpty()){
                    filteredItems = databaseMenuItems
                    populateDatabase = true
                }
                fun filterByCategory(category: String) {
                    if (category == "all"){
                        filteredItems = databaseMenuItems
                    } else {
                        filteredItems = databaseMenuItems.filter { it.category == category }
                    }
                }
                fun filterByQuery(query: String){
                    if (query.isNotBlank()){
                        filteredItems = databaseMenuItems.filter { it.title.contains(query, ignoreCase = true) }
                    }
                }
                MyNavigation(
                    sharedPreferences = sharedPreferences,
                    items = if (databaseMenuItems.isEmpty()) databaseMenuItems else filteredItems,
                    filterByCategory = ::filterByCategory,
                    filterByQuery = ::filterByQuery
                )
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuDao().isEmpty()){
                saveAllToDatabase(getMenuItems())
            }
        }
    }
    private suspend fun getMenuItems(): List<MenuItems> {
        val urlString = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response: MenuNetworkData = httpClient
            .get(urlString)
            .body()
        return response.menu
    }

    private fun saveAllToDatabase(menuItems: List<MenuItems>){
        val menuItemsToSave = menuItems.map { it.storeInDatabase() }
        database.menuDao().insertAll(*menuItemsToSave.toTypedArray())
    }
}