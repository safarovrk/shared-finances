package com.example.features.sharedfinance.list_journals.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.navigation.Screen
import com.example.core.theme.CustomTheme
import com.example.features.R
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.core.ui.SFToolbar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListJournalsScreen(
    navController: NavController,
    viewModel: ListJournalsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.getJournals() }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ListJournalsViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = context.resources.getString(event.messageId)
                    )
                }

                is ListJournalsViewModel.UiEvent.JournalSelected -> {
                    navController.navigate(Screen.Home.screenName)
                }

                is ListJournalsViewModel.UiEvent.TokenExpired -> {
                    navController.navigate(Screen.Login.screenName)
                }

                is ListJournalsViewModel.UiEvent.InvitationButtonClicked -> {
                    navController.navigate(Screen.Invitations.screenName)
                }

                is ListJournalsViewModel.UiEvent.LogoutButtonClicked -> {
                    navController.navigate(Screen.Login.screenName) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(ListJournalsEvent.CreationClick)
                },
                backgroundColor = CustomTheme.colors.button.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        topBar = {
            SFToolbar(
                title = stringResource(com.example.core.R.string.title_list_journals),
                onInvitationsIconClick = { viewModel.onEvent(ListJournalsEvent.OnInvitationsClicked) },
                onLogoutIconClick = { viewModel.onEvent(ListJournalsEvent.OnLogoutClicked) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullToRefreshState)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.journals) { journal ->
                    JournalItem(
                        journal = journal,
                        onItemClick = {
                            viewModel.onEvent(ListJournalsEvent.JournalChosen(it.journalName))
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            PullRefreshIndicator(
                state.isRefreshing,
                pullToRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = CustomTheme.colors.text.contrast
                )
            }
            if (state.errorMessageId != 0) {
                Text(
                    text = stringResource(state.errorMessageId),
                    color = CustomTheme.colors.text.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .wrapContentHeight()
                )
            }
            if (viewModel.state.value.isDialogShown) {
                JournalCreationDialog(
                    onDismiss = {
                        viewModel.onEvent(ListJournalsEvent.OnCreationDismiss)
                    },
                    onSubmit = {
                        viewModel.onEvent(ListJournalsEvent.OnCreationSubmit(it))
                    }
                )
            }
        }
    }
}
