package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
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
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
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
               reviewViewModel: ReviewViewModel = viewModel(),
               authViewModel: AuthViewModel = hiltViewModel()
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
            navController.navigate("JournalsScreen"){
                popUpTo(0)
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
        "ContentDescriptionScreen", "ContentReviewScreen" -> {
            topBarState.value = 2
        }
        else -> {
            topBarState.value = 3
        }
    }

    Scaffold(
        //modifier = modifier,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { when(topBarState.value){
            0-> JournalsToolbar(scrollBehavior = scrollBehavior)
            1-> GalleryToolbar(scrollBehavior = scrollBehavior, galleryViewModel)
            2-> ContentToolbar(navController = navController, descriptionViewModel, authViewModel)
        } },
        bottomBar = {
            if(topBarState.value != 3)
            bottomBar(navController = navController, currentDestination = currentDestination) },
        floatingActionButton = {
            if(topBarState.value != 3)
            FloatingActionButton(
                onClick = {
                    authViewModel.deleteUserById(authViewModel.uiState.user!!.id)
                    authViewModel.changeUser(null)
                    authViewModel.signOutUser()
                    navController.navigate("EntranceScreen"){
                        popUpTo(0)
                    }}
            ) {
                Icon(Icons.Filled.ExitToApp,"")
            }
        }
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
                        EntranceScreen(navController, authViewModel)
                    }
                    composable("JournalsScreen") {
                        JournalsScreen(navController)
                    }
                    composable("GalleryScreen") {
                        GalleryScreen(navController, galleryViewModel, descriptionViewModel)
                    }
                    composable("ContentDescriptionScreen") {
                        ContentDescriptionScreen(navController, descriptionViewModel)
                    }
                    composable("ContentReviewScreen") {
                        ContentReviewScreen(navController = navController)
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
private fun bottomBar(navController: NavController, currentDestination: String?){

    val navItems = listOf(
        BottomNavigationItem(
            title = "Journals",
            route= listOf("JournalsScreen", "ContentReviewScreen"),
            selectedIcon = Icons.Default.Article,
            unselectedIcon = Icons.Filled.Article,
        ),
        BottomNavigationItem(
            title = "Gallery",
            route=listOf("GalleryScreen", "ContentDescriptionScreen"),
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

