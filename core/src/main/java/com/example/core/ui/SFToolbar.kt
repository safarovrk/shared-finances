package com.example.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme

@Composable
fun SFToolbar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> (Unit))? = null,
    onInvitationsIconClick: (() -> (Unit))? = null,
    onLogoutIconClick: (() -> (Unit))? = null,
    icon: ImageVector = Icons.Default.ArrowBack,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = CustomTheme.typography.title.mediumLarge,
                color = CustomTheme.colors.text.primary,
                modifier = Modifier.padding(8.dp)
            )
        },
        modifier = modifier.fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = CustomTheme.colors.background.primary,
        navigationIcon = if (onNavigationIconClick != null) ({
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    icon,
                    contentDescription = icon.toString()
                )
            }
        }) else null,
        actions = {
            if (onInvitationsIconClick != null) {
                IconButton(
                    onClick = onInvitationsIconClick,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = Icons.Default.Email.name
                    )
                }
            }
            if (onLogoutIconClick != null) {
                IconButton(
                    onClick = onLogoutIconClick
                    , modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = Icons.Default.ExitToApp.name
                    )
                }
            }
        }
    )
}
