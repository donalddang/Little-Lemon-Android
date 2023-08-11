package com.example.little_lemon_android

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TextField
import androidx.compose.material3.Icon
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.TextStyle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage



@Composable
fun Home(
    navController: NavHostController,
    sharedPreferences: SharedPreferences,
    items: List<MenuItemDatabase>,
    filterByCategory: (String) -> Unit,
    filterBySearchQuery: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationView(navController)
        FilterSection(filterBySearchQuery)
        FilteredResultsSection(items, navController, filterByCategory)
    }


}

@Composable
private fun NavigationView(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.1f)
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .size(width = 50.dp, height = 50.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(filterBySearchQuery: (String) -> Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF4CE14)
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6650a4)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp)
        ){
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 28.dp, top = 20.dp)
                    .fillMaxWidth(0.6f),
                color = Color(0xFF6650a4)
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Food Image",
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillWidth
            )
        }
        TextField(
            value = searchQuery,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Default Microscope SF Symbol"
                )
            },
            onValueChange = {
                searchQuery = it
                filterBySearchQuery(it)
                Log.d("Filter: ", it)
            },
            label = { Text("Enter a Query...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 25.dp, end = 5.dp, bottom = 15.dp)
        )
    }
}

@Composable
fun FilteredResultsSection(
    items: List<MenuItemDatabase>,
    navController: NavHostController,
    filterByCategory: (String) -> Unit
){
    CategoryFilters(filterByCategory)
    Divider(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
        color = Color(0xFFEDEFEE),
        thickness = 2.dp
    )
    Cell(items, navController)
}

@Composable
fun CategoryFilters(filterByCategory: (String) -> Unit)  {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        Text(
            "Order for Delivery",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .fillMaxWidth()
                .padding(top = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    filterByCategory("all")
                }
            ) {
                Text("All")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    filterByCategory("starters")
                }
            ) {
                Text("Starters")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    filterByCategory("mains")
                }
            ) {
                Text("Mains")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    filterByCategory("desserts")
                }
            ) {
                Text("Desserts")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    filterByCategory("drinks")
                }
            ) {
                Text("Drinks")
            }
        }
    }
}

@Composable
fun Cell(items: List<MenuItemDatabase>, navController: NavHostController){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ){
        items(
            items = items,
            itemContent = {item ->
                ItemCell(item, navController)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCell(item: MenuItemDatabase, navController: NavHostController){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(10.dp)
        ){
            Text(
                item.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                item.description,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 5.dp),
                style = TextStyle(color = Color.DarkGray)
            )

            Text(
                text = "%.2f".format(item.price),
                style = TextStyle(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        GlideImage(
            model = item.image,
            contentDescription = "Food item picture",
            modifier = Modifier
                .padding(10.dp)
                .height(100.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillHeight
        )
    }
}