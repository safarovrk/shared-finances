package com.example.features.sharedfinance.home.categories.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme
import com.example.core.utils.Constants
import com.example.features.sharedfinance.home.categories.domain.Category
import com.example.features.sharedfinance.invitations.domain.Invitation

@Composable
fun CategoryItem(
    category: Category,
    userRole: Long,
    onDeleteClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.name,
            style = CustomTheme.typography.title.mediumLarge,
            color = CustomTheme.colors.text.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight().padding(10.dp)
        )
        if (userRole == Constants.ADMIN_ROLE_ID || userRole == Constants.ADULT_ROLE_ID) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onDeleteClick(category) }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = Icons.Default.Delete.name,
                    tint = CustomTheme.colors.icon.disabled,
                )
            }
        }
    }
    Divider(color = CustomTheme.colors.background.photo, thickness = 1.dp)
}