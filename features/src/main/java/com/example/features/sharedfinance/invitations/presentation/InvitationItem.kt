package com.example.features.sharedfinance.invitations.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme
import com.example.features.sharedfinance.invitations.domain.Invitation

@Composable
fun InvitationItem(
    invitation: Invitation,
    onAcceptClick: (Invitation) -> Unit,
    onDeclineClick: (Invitation) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = invitation.journalName.journalName,
                style = CustomTheme.typography.title.mediumLarge,
                color = CustomTheme.colors.text.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = invitation.role.name,
                style = CustomTheme.typography.text.mediumNormal,
                color = CustomTheme.colors.text.secondary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                IconButton(
                    onClick = { onAcceptClick(invitation) }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Done,
                        contentDescription = Icons.Default.Done.name,
                        tint = CustomTheme.colors.icon.disabled,
                    )
                }
                IconButton(
                    onClick = { onDeclineClick(invitation) }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = Icons.Default.Close.name,
                        tint = CustomTheme.colors.icon.disabled,
                    )
                }
            }
        }
    }
    Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
}