package com.example.tstrade.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes
    val title: Int,
    @StringRes
    val descriptionId: Int,
    @DrawableRes
    val drawableId: Int,
)
