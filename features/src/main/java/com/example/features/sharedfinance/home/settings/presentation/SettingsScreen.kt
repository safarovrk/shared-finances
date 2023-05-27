package com.example.features.sharedfinance.home.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FolderDelete
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.navigation.Router
import com.example.core.navigation.Screen
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFToolbar
import com.example.core.utils.Constants
import com.example.features.R
import com.example.features.sharedfinance.list_journals.presentation.JournalCreationDialog
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsEvent
import com.example.features.sharedfinance.login.presentation.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    router: Router,
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val roleState = viewModel.userRoleState.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SettingsViewModel.UiEvent.LogOut -> {
                    router.routeTo(Screen.Login.screenName)
                }

                is SettingsViewModel.UiEvent.RouteToChangeJournal -> {
                    router.routeTo(Screen.ListJournals.screenName)
                }

                is SettingsViewModel.UiEvent.RouteToInvitations -> {
                    router.routeTo(Screen.Invitations.screenName)
                }

                is SettingsViewModel.UiEvent.ShowSnackBar -> {
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
                title = stringResource(com.example.core.R.string.title_settings)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {

                Column(modifier = Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = Icons.Default.Person.name,
                            tint = CustomTheme.colors.icon.disabled
                        )
                        Text(
                            text = stringResource(R.string.scoped_info),
                            style = CustomTheme.typography.text.mediumNormal,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.your_login),
                            style = CustomTheme.typography.text.mediumNormal
                        )
                        Text(
                            text = viewModel.currentInfoState.value.login,
                            style = CustomTheme.typography.text.mediumNormal
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.current_journal),
                            style = CustomTheme.typography.text.mediumNormal
                        )
                        Text(
                            text = viewModel.currentInfoState.value.journalName,
                            style = CustomTheme.typography.text.mediumNormal
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.your_role),
                            style = CustomTheme.typography.text.mediumNormal
                        )
                        Text(
                            text = viewModel.currentInfoState.value.role,
                            style = CustomTheme.typography.text.mediumNormal
                        )
                    }
                }
                Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEvent(SettingsEvent.ChangeJournalClicked) }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmarks,
                        contentDescription = Icons.Default.LibraryAdd.name,
                        tint = CustomTheme.colors.icon.disabled
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(R.string.change_journal),
                        style = CustomTheme.typography.text.mediumNormal,
                        color = CustomTheme.colors.text.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
                Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEvent(SettingsEvent.InvitationsClicked) }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = Icons.Default.Email.name,
                        tint = CustomTheme.colors.icon.disabled
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(R.string.invitations),
                        style = CustomTheme.typography.text.mediumNormal,
                        color = CustomTheme.colors.text.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
                Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
                if (roleState.role == Constants.ADMIN_ROLE_ID) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onEvent(SettingsEvent.InviteUserClicked) }
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = Icons.Default.PersonAdd.name,
                            tint = CustomTheme.colors.icon.disabled
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = stringResource(R.string.invite_user),
                            style = CustomTheme.typography.text.mediumNormal,
                            color = CustomTheme.colors.text.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentHeight()
                        )
                    }
                    Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEvent(SettingsEvent.ExitJournalClicked) }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkRemove,
                        contentDescription = Icons.Default.BookmarkRemove.name,
                        tint = CustomTheme.colors.icon.disabled
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(R.string.exit_from_journal),
                        style = CustomTheme.typography.text.mediumNormal,
                        color = CustomTheme.colors.text.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
                Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEvent(SettingsEvent.LogOutClicked) }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = Icons.Default.ExitToApp.name,
                        tint = CustomTheme.colors.icon.disabled
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(R.string.exit),
                        style = CustomTheme.typography.text.mediumNormal,
                        color = CustomTheme.colors.text.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
                Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
                if (viewModel.state.value.isDialogShown) {
                    InviteUserDialog(
                        onDismiss = {
                            viewModel.onEvent(SettingsEvent.OnInvitationDismiss)
                        },
                        onSubmit = { userName, role ->
                            viewModel.onEvent(SettingsEvent.OnInvitationSubmit(userName, role))
                        }
                    )
                }
            }
        }
    }

}