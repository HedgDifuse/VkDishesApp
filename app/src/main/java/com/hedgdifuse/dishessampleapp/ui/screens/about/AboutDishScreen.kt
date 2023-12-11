package com.hedgdifuse.dishessampleapp.ui.screens.about

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hedgdifuse.dishessampleapp.ui.components.Price
import com.hedgdifuse.dishessampleapp.ui.components.screen.ErrorScreen
import com.hedgdifuse.dishessampleapp.ui.components.screen.LoadingScreen
import com.hedgdifuse.dishessampleapp.ui.state.about.AboutDishScreenState
import com.hedgdifuse.dishessampleapp.ui.theme.DishesSampleAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutDishScreen(
    dishId: String?,
    navController: NavController,
    viewModel: AboutDishViewModel = hiltViewModel<AboutDishViewModel>().apply { load(dishId) }
) {
    val screenState by viewModel.screenState.collectAsState()

    DishesSampleAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = { Text((screenState as? AboutDishScreenState.Loaded)?.dish?.name.orEmpty()) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Crossfade(
                targetState = screenState,
                modifier = Modifier.padding(padding),
                label = ""
            ) {
                when(it) {
                    AboutDishScreenState.Loading -> LoadingScreen()
                    is AboutDishScreenState.Error -> ErrorScreen(
                        errorText = it.errorText,
                        icon = Icons.Default.Delete
                    )
                    is AboutDishScreenState.Loaded -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            AsyncImage(
                                model = it.dish.image,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(it.dish.description.orEmpty())

                                Price(it.dish.price,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End)
                            }
                        }
                    }
                }
            }
        }
    }
}
