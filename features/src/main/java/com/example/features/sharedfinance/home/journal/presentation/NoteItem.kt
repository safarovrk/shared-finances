package com.example.features.sharedfinance.home.journal.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme
import com.example.core.utils.Constants
import com.example.features.sharedfinance.home.journal.domain.response_entity.Note

@Composable
fun NoteItem(
    note: Note,
    userRole: Long,
    modifier: Modifier = Modifier,
    onEditClick: (Note) -> Unit,
    onDeleteClick: (Long) -> Unit
) {

    Column(
        modifier = modifier.padding(vertical = 12.dp, horizontal = 24.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.date,
                style = CustomTheme.typography.text.mediumNormal,
                color = CustomTheme.colors.text.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentHeight()
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.category.name,
                style = CustomTheme.typography.title.semiBoldLarge,
                color = CustomTheme.colors.text.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentHeight()
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = note.sum.toString(),
                style = CustomTheme.typography.title.semiBoldLarge,
                color = if(note.sum > 0) Color.Green else if(note.sum < 0) Color.Red else Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentHeight()
            )
        }
        if (note.comment.isNotEmpty()) {
            Spacer(modifier = Modifier.size(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.comment,
                    style = CustomTheme.typography.text.mediumNormal,
                    color = CustomTheme.colors.text.primary,
                    modifier = Modifier.wrapContentHeight()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            if (userRole == Constants.ADMIN_ROLE_ID || userRole == Constants.ADULT_ROLE_ID) {
                IconButton(
                    onClick = { onEditClick(note) }
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = Icons.Default.Edit.name,
                        tint = CustomTheme.colors.icon.disabled,
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(
                    onClick = { onDeleteClick(note.id) }
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = Icons.Default.Delete.name,
                        tint = CustomTheme.colors.icon.disabled,
                    )
                }
            }
        }
    }
    Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
}