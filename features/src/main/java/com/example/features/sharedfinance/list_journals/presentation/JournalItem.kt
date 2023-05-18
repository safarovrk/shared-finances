package com.example.features.sharedfinance.list_journals.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme
import com.example.features.R
import com.example.features.sharedfinance.list_journals.domain.Journal

@Composable
fun JournalItem(
    journal: Journal,
    onItemClick: (Journal) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(journal) }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = journal.journalName,
                style = CustomTheme.typography.title.mediumLarge,
                color = CustomTheme.colors.text.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.members),
                style = CustomTheme.typography.text.mediumNormal,
                color = CustomTheme.colors.text.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = journal.getLoginsInString(),
                style = CustomTheme.typography.text.mediumNormal,
                color = CustomTheme.colors.text.primary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = Icons.Default.ArrowRight.name,
                tint = CustomTheme.colors.icon.disabled,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
    Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)

}