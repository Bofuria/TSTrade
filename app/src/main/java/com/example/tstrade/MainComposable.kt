package com.example.tstrade

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.tstrade.navigation.DrawerItems
import com.example.tstrade.navigation.NavigationDrawerContent
import com.example.tstrade.navigation.RouteOptions
import com.example.tstrade.presentation.auth.AuthViewModel
import com.example.tstrade.presentation.auth.EmailScreen
import com.example.tstrade.presentation.auth.LoginScreen
import com.example.tstrade.presentation.events.EventsScreen
import com.example.tstrade.presentation.events.EventsViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(currentUser: FirebaseUser) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerItems.drawerItems,
                defaultPick = RouteOptions.EventsScreen) { onUserPickedOption ->
                when(onUserPickedOption) {
                    RouteOptions.EventsScreen -> {
                        navController.navigate(onUserPickedOption.name)
                    }
                    RouteOptions.LoginScreen -> {
                        navController.navigate(onUserPickedOption.name)
                    }
                    RouteOptions.EmailLoginScreen -> {
                        navController.navigate(onUserPickedOption.name)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Drawer")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if(isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    NavHost(navController = navController, startDestination = RouteOptions.EventsScreen.name) {
                        mainScreenRoute(navController = navController, currentUser = currentUser)
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.mainScreenRoute(navController: NavController, currentUser: FirebaseUser) {
    composable(RouteOptions.EventsScreen.name) {
        val viewModel = hiltViewModel<EventsViewModel>()
        EventsScreen(
            viewModel = viewModel,
            navController = navController,
            currentUser = currentUser)
    }
    navigation(startDestination = RouteOptions.LoginScreen.name, route = "login") {
        composable(RouteOptions.LoginScreen.name) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("login")
            }
            val viewModel = hiltViewModel<AuthViewModel>()
            LoginScreen(viewModel)
        }
        composable(RouteOptions.EmailLoginScreen.name) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("login")
            }
            EmailScreen()
        }
    }
}