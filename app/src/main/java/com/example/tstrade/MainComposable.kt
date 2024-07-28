package com.example.tstrade

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tstrade.navigation.Route
import com.example.tstrade.presentation.events.EventsScreen
import com.example.tstrade.presentation.events.EventsViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = Route.MainScreen) {
                    mainScreenRoute(navController = navController)
                }
            }
        }
    )
}

private fun NavGraphBuilder.mainScreenRoute(navController: NavController) {
    composable(Route.MainScreen) {
        val viewModel = hiltViewModel<EventsViewModel>()
        EventsScreen(viewModel = viewModel)
    }
}