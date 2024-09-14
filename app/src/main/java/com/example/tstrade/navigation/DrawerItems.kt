package com.example.tstrade.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import com.example.tstrade.R

object DrawerItems {
    val drawerItems = arrayListOf(
        DrawerItemInfo(
            RouteOptions.EventsScreen,
            R.string.drawer_events,
            R.string.events_description,
            R.drawable.event_list_24px
        )
    )
}