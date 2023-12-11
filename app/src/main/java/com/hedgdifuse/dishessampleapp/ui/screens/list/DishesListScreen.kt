package com.hedgdifuse.dishessampleapp.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hedgdifuse.dishessampleapp.MainActivity.Companion.LIST_SCREEN
import com.hedgdifuse.dishessampleapp.R
import com.hedgdifuse.dishessampleapp.domain.Dish
import com.hedgdifuse.dishessampleapp.ui.components.DishCard
import com.hedgdifuse.dishessampleapp.ui.components.screen.ErrorScreen
import com.hedgdifuse.dishessampleapp.ui.components.screen.LoadingScreen
import com.hedgdifuse.dishessampleapp.ui.state.list.ButtonState
import com.hedgdifuse.dishessampleapp.ui.state.list.DishesScreenState
import kotlinx.coroutines.launch

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun DishesScreen(
    navController: NavController,
    viewModel: DishesListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    val markedDishes by viewModel.markedDishes.collectAsState(emptySet())
    val buttonState by viewModel.buttonState.collectAsState(ButtonState.DISABLED)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            when(buttonState) {
                ButtonState.ACTIVE -> ExtendedFloatingActionButton(
                    text = { Text(stringResource(id = R.string.remove)) },
                    icon = {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            viewModel.removeMarked()
                        }
                    }
                )
                ButtonState.LOADING -> {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        CircularProgressIndicator()
                    }
                }
                ButtonState.DISABLED -> {}
            }
        }
    ) { padding ->
        Crossfade(screenState, label = "") {
            when (it) {
                is DishesScreenState.Error -> {
                    ErrorScreen(
                        onRetryClick = { coroutineScope.launch { viewModel.retry() } },
                        icon = Icons.Default.Warning,
                        errorText = it.errorText
                    )
                }

                DishesScreenState.Loading -> {
                    LoadingScreen()
                }

                is DishesScreenState.Loaded -> {
                    if (it.dishes.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null)
                                Text(stringResource(R.string.no_dishes))
                            }
                        }

                        return@Crossfade
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = if (buttonState != ButtonState.DISABLED) 86.dp else 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(padding)
                    ) {
                        items(it.dishes, key = Dish::id, contentType = { true }) { dish ->
                            DishCard(
                                dish = dish,
                                checked = markedDishes.contains(dish.id),
                                onCheckedChange = {
                                    coroutineScope.launch {
                                        viewModel.mark(dish, it)
                                    }
                                },
                                onClick = { navController.navigate("$LIST_SCREEN/${dish.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}
