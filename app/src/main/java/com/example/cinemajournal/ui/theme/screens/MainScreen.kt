package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemajournal.MainActivity
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MainScreen(context: MainActivity,
               galleryViewModel: GalleryViewModel = hiltViewModel(),
               descriptionViewModel: DescriptionViewModel = viewModel(),
               reviewViewModel: ReviewViewModel = hiltViewModel(),
               authViewModel: AuthViewModel = hiltViewModel(),
               journalsViewModel: JournalsViewModel = hiltViewModel(),
               additionViewModel: AdditionViewModel = hiltViewModel(),
               ){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val topBarState = rememberSaveable { (mutableStateOf(0)) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    //if(!authViewModel.uiState.isLoginProcess && )
    //authViewModel.getAllUser()

    if(currentDestination == "EntranceScreen"){
        if(!authViewModel.uiState.isRefreshLoginProcess){
            authViewModel.getAllUser()
        }
        if(authViewModel.uiState.user != null){
            if(!isOnline(context)){
                galleryViewModel.refreshTops(authViewModel.uiState.user?.role?:0)
                navController.navigate("JournalsScreen"){
                    popUpTo(0)
                    Log.d("R", "${journalsViewModel.uiState.user}",)
                }
            }else {
                journalsViewModel.getUserFromDB(authViewModel.uiState.user!!.id)
                Log.d("R", "проверяем вход\n${authViewModel.uiState.user}\n${journalsViewModel.uiState.user}",)
                if(journalsViewModel.uiState.user != null){
                    journalsViewModel.startUpdateLocalDB(journalsViewModel.uiState.user!!)
                    galleryViewModel.refreshTops(authViewModel.uiState.user?.role?:0)
                    navController.navigate("JournalsScreen"){
                        popUpTo(0)
                        Log.d("R", "${journalsViewModel.uiState.user}",)
                    }
                }
            }
        }

    }

    /*if (authViewModel.uiState.user != null){
        navController.navigate("JournalsScreen"){
            popUpTo(0)
        }
    }*/

    //authViewModel.refreshUser()
    //galleryViewModel.refreshTops()

//    GlobalScope.launch(Dispatchers.Main) {
//        Log.d("R", "начало скоп", )
//        users = authViewModel.uiState.allUsers
//        Log.d("R", users.toString(), )
//    }

    var startDestination = "EntranceScreen"

//    var startDestination = if (
//        GlobalScope.launch(Dispatchers.Main) {
//            authViewModel.uiState.allUsers.isNotEmpty()
//        }
//    )  {
//        authViewModel.changeUser(authViewModel.uiState.allUsers[0])
//        "JournalsScreen"
//    } else {
//        "EntranceScreen"
//    }

    when (navBackStackEntry?.destination?.route) {
        "JournalsScreen" -> {
            topBarState.value = 0
        }
        "GalleryScreen" -> {
            topBarState.value = 1
        }
        "ContentDescriptionScreen", "LocalContentDescriptionScreen", "ContentReviewScreen" -> {
            topBarState.value = 2
        }
        "ContentAdditionScreen" -> {
            topBarState.value = 3
        }
        else -> {
            topBarState.value = 4
        }
    }

    Scaffold(
        //modifier = modifier,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { when(topBarState.value){
            0-> JournalsToolbar(scrollBehavior = scrollBehavior, navController = navController, journalsViewModel = journalsViewModel)
            1-> GalleryToolbar(scrollBehavior = scrollBehavior, galleryViewModel, authViewModel)
            2-> ContentToolbar(navController = navController, currentDestination?:"JournalsScreen", descriptionViewModel, reviewViewModel, authViewModel, journalsViewModel)
            3-> AdditionToolbar(scrollBehavior = scrollBehavior, navController = navController, additionViewModel = additionViewModel)
        } },
        bottomBar = {
            if(topBarState.value != 3)
            bottomBar(navController = navController, currentDestination = currentDestination, descriptionViewModel) },
        /*floatingActionButton = {
            if(topBarState.value != 3)
            FloatingActionButton(
                onClick = {
                    authViewModel.deleteUserById(authViewModel.uiState.user!!.id)
                    journalsViewModel.deleteAllMoviesFromLocalDB()
                    authViewModel.changeUser(null)
                    journalsViewModel.changeUser(null)
                    authViewModel.signOutUser()
                    navController.navigate("EntranceScreen"){
                        popUpTo(0)
                    }}
            ) {
                Icon(Icons.Filled.ExitToApp,"")
            }
        }*/
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                NavHost(navController = navController, startDestination = startDestination) {
                    composable("EntranceScreen") {
                        EntranceScreen(navController, authViewModel, journalsViewModel, galleryViewModel)
                    }
                    composable("JournalsScreen") {
                        JournalsScreen(navController, journalsViewModel, reviewViewModel, galleryViewModel, descriptionViewModel, authViewModel)
                    }
                    composable("ContentAdditionScreen") {
                        ContentAdditionScreen(navController, additionViewModel, authViewModel)
                    }
                    composable("GalleryScreen") {
                        GalleryScreen(navController, galleryViewModel, descriptionViewModel, authViewModel)
                    }
                    composable("ContentDescriptionScreen") {
                        ContentDescriptionScreen(navController, descriptionViewModel, authViewModel, journalsViewModel, context)
                    }
                    composable("LocalContentDescriptionScreen") {
                        ContentDescriptionScreen(navController, descriptionViewModel, authViewModel, journalsViewModel, context)
                    }
                    composable("ContentReviewScreen") {
                        ContentReviewScreen(navController = navController, reviewViewModel, authViewModel, context)
                    }
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val route: List<String>,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
private fun bottomBar(navController: NavController, currentDestination: String?, descriptionViewModel: DescriptionViewModel){

    val navItems = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.journals),
            //route= if(descriptionViewModel.uiState.movieInfo != null)listOf("JournalsScreen", "ContentReviewScreen") else listOf("JournalsScreen", "ContentReviewScreen", "ContentDescriptionScreen"),
            route = listOf("JournalsScreen", "ContentAdditionScreen", "ContentReviewScreen", "LocalContentDescriptionScreen"),
            selectedIcon = Icons.Default.Article,
            unselectedIcon = Icons.Filled.Article,
        ),
        BottomNavigationItem(
            title = stringResource(R.string.gallery),
            //route=if(descriptionViewModel.uiState.movieInfo != null)listOf("GalleryScreen", "ContentDescriptionScreen") else listOf("GalleryScreen"),
            route = listOf("GalleryScreen", "ContentDescriptionScreen"),
            selectedIcon = Icons.Default.ManageSearch,
            unselectedIcon = Icons.Filled.ManageSearch
        )
    )

    val selectedItemIndex = navItems.indexOfFirst { it.route.contains(currentDestination)}

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    if (selectedItemIndex != index) {
                        //selectedItemIndex = index
                        navController.navigate(item.route.first()) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if(index==selectedItemIndex){
                            item.selectedIcon
                        }else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {Text(text=item.title)}
            )
        }
    }

}

fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

