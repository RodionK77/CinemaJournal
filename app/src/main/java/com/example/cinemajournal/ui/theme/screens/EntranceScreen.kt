package com.example.cinemajournal.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemajournal.data.models.SignInRequest
import com.example.cinemajournal.data.models.SignUpRequest
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel


@Composable
fun EntranceScreen(navController: NavController, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(ScrollState(0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val context = LocalContext.current

        if (authViewModel.uiState.buttonRegistrationState) {
            checkAuth(context, authViewModel)
        }

        if (authViewModel.uiState.buttonLoginState) {
            checkLogin(navController, context, authViewModel, journalsViewModel)
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = authViewModel.uiState.emailText,
            label = { Text(text = "Email") },
            onValueChange = {
                authViewModel.changeEmailText(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
            /*colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),*/
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = authViewModel.uiState.passwordText,
            label = { Text(text = "Password") },
            onValueChange = {
                authViewModel.changePasswordText(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
            /*colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),*/
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!authViewModel.uiState.isLoginScreen) {
            registrationScreen(context = context, authViewModel = authViewModel)
        } else {
            loginScreen(navController = navController, context = context, authViewModel = authViewModel)
        }

    }
}

@Composable
fun checkAuth(context: Context, authViewModel: AuthViewModel) {

    when (authViewModel.uiState.returnMessage) {
        "HTTP 400 " -> {
            Toast.makeText(
                context,
                "Пользователь с указанным Email уже зарегистрирован",
                Toast.LENGTH_SHORT
            ).show()

            authViewModel.changeButtonRegistrationState(false)
            authViewModel.changeReturnMessage("")
        }

        "HTTP 401 " -> {
            Toast.makeText(
                context,
                "Ошибка регистрации",
                Toast.LENGTH_SHORT
            ).show()
            authViewModel.changeButtonRegistrationState(false)
            authViewModel.changeReturnMessage("")
        }

        "" -> {}
        else -> {
            Toast.makeText(
                context,
                "Пользователь успешно зарегистрирован",
                Toast.LENGTH_SHORT
            ).show()
            authViewModel.changeButtonRegistrationState(false)
            authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
            authViewModel.changeReturnMessage("")
        }
    }
}

@Composable
fun checkLogin(navController: NavController, context: Context, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel) {

    if(authViewModel.uiState.user != null){

        journalsViewModel.getUserFromDB(authViewModel.uiState.user!!.id)

        if(journalsViewModel.uiState.user != null){

            journalsViewModel.startUpdateLocalDB(journalsViewModel.uiState.user!!)

            authViewModel.saveUserToDatabase(authViewModel.uiState.user!!)

            navController.navigate("JournalsScreen"){
                popUpTo(0)
            }

            authViewModel.changeButtonLoginState(false)
        }


    }else if(!authViewModel.uiState.isLoginProcess){
        Toast.makeText(
            context,
            "Вход не удался",
            Toast.LENGTH_SHORT
        ).show()

        authViewModel.changeButtonLoginState(false)
    }
}

@Composable
fun registrationScreen(context: Context, authViewModel: AuthViewModel) {
    Button(
        onClick = {
            if (authViewModel.uiState.emailText.isNotEmpty() && authViewModel.uiState.passwordText.isNotEmpty()) {

                var data = SignUpRequest(
                    username = authViewModel.uiState.emailText,
                    email = authViewModel.uiState.emailText,
                    password = authViewModel.uiState.passwordText,
                    role = 0
                )
                authViewModel.signUpUser(data)

                authViewModel.changeButtonRegistrationState(true)

            } else Toast.makeText(
                context,
                "поля не должны быть пустыми",
                Toast.LENGTH_SHORT
            ).show()

        },
    )
    {
        Column {
            Text("Регистрация", fontSize = 24.sp)
        }
    }

    TextButton(onClick = {
        authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
    })
    {
        Column {
            Text("Вход в аккаунт", fontSize = 18.sp)
        }
    }
}

@Composable
fun loginScreen(navController: NavController, context: Context, authViewModel: AuthViewModel) {
    Button(
        onClick = {
            if (authViewModel.uiState.emailText.isNotEmpty() && authViewModel.uiState.passwordText.isNotEmpty()) {

                var data = SignInRequest(
                    username = authViewModel.uiState.emailText,
                    password = authViewModel.uiState.passwordText
                )
                authViewModel.signInUser(data)

                authViewModel.changeButtonLoginState(true)

            } else Toast.makeText(
                context,
                "поля не должны быть пустыми",
                Toast.LENGTH_SHORT
            ).show()
        },
    )
    {
        Column {
            Text("Вход", fontSize = 24.sp)
        }
    }

    TextButton(onClick = { authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen) })
    {
        Column {
            Text("Регистрация аккаунта", fontSize = 18.sp)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun EntrancePreview() {
//    AppTheme {
//        EntranceScreen()
//    }
//}