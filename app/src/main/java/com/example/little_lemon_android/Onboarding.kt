package com.example.little_lemon_android

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController, sharedPreferences: SharedPreferences){
    var firstNameText by remember {
        mutableStateOf("")
    }
    var lastNameText by remember {
        mutableStateOf("")
    }
    var emailText by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    //add image here


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 50.dp, height = 50.dp)
        )
        Text(text = "Let's get to know you",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = Color.Green)
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 30.dp)
        )
        Text("First Name")
        TextField(
            value = firstNameText,
            onValueChange = {firstNameText = it},
            label = { Text("First name") },
            textStyle = TextStyle.Default,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 0.dp, end = 25.dp, bottom = 0.dp)
        )
        Text("Last Name")
        TextField(
            value = lastNameText,
            onValueChange = { lastNameText = it},
            label = { Text("Last name") },
            textStyle = TextStyle.Default,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 0.dp, end = 25.dp, bottom = 20.dp)
        )
        Text("Email")
        TextField(
            value = emailText,
            onValueChange = {emailText = it},
            label = { Text("Email") },
            textStyle = TextStyle.Default,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 0.dp, end = 25.dp, bottom = 0.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            ),
            onClick = {
                if (firstNameText.isNotBlank() && lastNameText.isNotBlank() && emailText.isNotBlank()) {
                    // Registration successful
                    Toast.makeText(
                        context,
                        "Registration Successful!",
                        Toast.LENGTH_SHORT
                    ).show()

                    //apply shared preferences
                    with(sharedPreferences.edit()){
                        putString("firstName", firstNameText)
                        putString("lastName", lastNameText)
                        putString("email", emailText)
                        apply()
                    }


                    // Navigate to the Home screen
                    navController.navigate(Home.route)
                } else {
                    Toast.makeText(
                        context,
                        "Registration Unsuccessful. Please enter all data.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ){
            Text("Register")
        }
    }

}