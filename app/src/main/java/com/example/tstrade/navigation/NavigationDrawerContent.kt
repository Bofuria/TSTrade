package com.example.tstrade.navigation

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

@Composable
fun<T: Enum<T>> NavigationDrawerContent(
    drawerState: DrawerState,
    menuItems: List<DrawerItemInfo<T>>,
    defaultPick: T,
    onClick: (T) -> Unit
) {

    var currentPick by remember { mutableStateOf(defaultPick) }
    var coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(menuItems) { item ->
                        AppDrawerItem(item = item) { navOptions ->

                            if(currentPick == navOptions) {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                return@AppDrawerItem
                            }

                            currentPick = navOptions
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onClick(navOptions)
                        }
                    }
                }
            }
        }
    }
}