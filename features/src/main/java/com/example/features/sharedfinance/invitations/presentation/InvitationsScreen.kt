package com.example.features.sharedfinance.invitations.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFToolbar
import com.example.features.R
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvitationsScreen(
    navController: NavController, viewModel: InvitationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val pullToRefreshState = rememberPullRefreshState(refreshing = state.isRefreshing,
        onRefresh = { viewModel.getInvitations() })

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is InvitationViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = context.resources.getString(event.messageId)
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SFToolbar(
                title = stringResource(com.example.core.R.string.title_invitations)
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
                items(state.invitations) { invitation ->
                    InvitationItem(invitation = invitation, onAcceptClick = {
                        viewModel.onEvent(InvitationsEvent.OnAcceptClicked(it.journalName.journalName))
                    }, onDeclineClick = {
                        viewModel.onEvent(InvitationsEvent.OnDeclineClicked(it.journalName.journalName))
                    })
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            PullRefreshIndicator(
                state.isRefreshing, pullToRefreshState, Modifier.align(
                    Alignment.TopCenter
                )
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
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
        }
    }
}

