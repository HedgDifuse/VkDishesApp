package com.hedgdifuse.dishessampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hedgdifuse.dishessampleapp.ui.screens.about.AboutDishScreen
import com.hedgdifuse.dishessampleapp.ui.screens.list.DishesScreen
import com.hedgdifuse.dishessampleapp.ui.theme.DishesSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DishesSampleAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LIST_SCREEN,
                    enterTransition = { fadeIn(animationSpec = tween(150)) },
                    exitTransition = { fadeOut(animationSpec = tween(150)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(150)) },
                    popExitTransition = { fadeOut(animationSpec = tween(150)) }
                ) {
                    composable(LIST_SCREEN) { DishesScreen(navController) }
                    composable(ABOUT_SCREEN,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) {
                        AboutDishScreen(
                            dishId = it.arguments?.getString("id"),
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val LIST_SCREEN = "dishes"
        const val ABOUT_SCREEN = "dishes/{id}"
    }
}
