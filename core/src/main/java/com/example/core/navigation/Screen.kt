package com.example.core.navigation

import com.example.core.R

sealed class Screen(val screenName: String, val titleResourceId: Int, val icon: Int = -1, val focused_icon: Int = -1) {
    object Splash: Screen("splash", -1)
    object Login: Screen("login", R.string.title_login)
    object Registration: Screen("registration", R.string.title_registration)
    object ListJournals: Screen("list_journals", R.string.title_list_journals)
    object Home: Screen("home", R.string.title_home)
    object Journal: Screen(
        "journal",
        R.string.title_journal,
        icon = R.drawable.bookmark,
        focused_icon = R.drawable.bookmark_focused
    )
    object Categories: Screen(
        "categories",
        R.string.title_categories,
        icon = R.drawable.category,
        focused_icon = R.drawable.category_focused
    )
    object Chart: Screen(
        "chart",
        R.string.title_chart,
        icon = R.drawable.chart,
        focused_icon = R.drawable.chart_focused
    )
    object Settings: Screen(
        "settings",
        R.string.title_settings,
        icon = R.drawable.settings_applications,
        focused_icon = R.drawable.settings_applications_focused
    )
    object Invitations: Screen("invitations", R.string.title_invitations)

}