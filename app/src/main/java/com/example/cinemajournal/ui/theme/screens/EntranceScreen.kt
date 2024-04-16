package com.example.cinemajournal.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemajournal.R
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
            label = { Text(text = stringResource(R.string.email)) },
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
            label = { Text(text = stringResource(R.string.password)) },
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

        if (!authViewModel.uiState.isLoginScreen) {

            Row(
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = authViewModel.uiState.checkedState,
                    onCheckedChange = { authViewModel.changeCheckedState(it) }
                )
                Text("Детский аккаунт", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            registrationScreen(context = context, authViewModel = authViewModel)
        } else {
            Spacer(modifier = Modifier.height(24.dp))
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
                stringResource(R.string.email_is_busy),
                Toast.LENGTH_SHORT
            ).show()

            authViewModel.changeButtonRegistrationState(false)
            authViewModel.changeReturnMessage("")
        }

        "HTTP 401 " -> {
            Toast.makeText(
                context,
                stringResource(R.string.registration_error),
                Toast.LENGTH_SHORT
            ).show()
            authViewModel.changeButtonRegistrationState(false)
            authViewModel.changeReturnMessage("")
        }

        "" -> {}
        else -> {
            Toast.makeText(
                context,
                stringResource(R.string.successful_registration),
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
            stringResource(R.string.login_failed),
            Toast.LENGTH_SHORT
        ).show()

        authViewModel.changeButtonLoginState(false)
    }
}

@Composable
fun registrationScreen(context: Context, authViewModel: AuthViewModel) {
    val empty_error_message = stringResource(R.string.empty_fields)
    Log.d(
        "R",
        "User: ${authViewModel.uiState.checkedState}",
    )

    Button(

        onClick = {
            if (authViewModel.uiState.emailText.isNotEmpty() && authViewModel.uiState.passwordText.isNotEmpty()) {

                var data = SignUpRequest(
                    username = authViewModel.uiState.emailText,
                    email = authViewModel.uiState.emailText,
                    password = authViewModel.uiState.passwordText,
                    role = authViewModel.uiState.checkedState.compareTo(false)
                )
                authViewModel.signUpUser(data)

                authViewModel.changeButtonRegistrationState(true)

            } else Toast.makeText(
                context,
                empty_error_message,
                Toast.LENGTH_SHORT
            ).show()

        },
    )
    {
        Column {
            Text(stringResource(R.string.registration), fontSize = 24.sp)
        }
    }

    TextButton(onClick = {
        authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
    })
    {
        Column {
            Text(stringResource(R.string.entrance_in_account), fontSize = 18.sp)
        }
    }
}

@Composable
fun loginScreen(navController: NavController, context: Context, authViewModel: AuthViewModel) {
    val empty_error_message = stringResource(R.string.select_a_time)
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
                empty_error_message,
                Toast.LENGTH_SHORT
            ).show()
        },
    )
    {
        Column {
            Text(stringResource(R.string.entrance), fontSize = 24.sp)
        }
    }

    TextButton(onClick = { authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen) })
    {
        Column {
            Text(stringResource(R.string.registration_account), fontSize = 18.sp)
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