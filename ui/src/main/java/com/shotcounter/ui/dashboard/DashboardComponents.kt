package com.shotcounter.ui.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shotcounter.ui.R

@Composable
fun DashboardHeading() {
    Text(
        text = stringResource(R.string.dahboard_title),
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
fun RecordNewShootButton(onClick: (DashboardIntent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick(DashboardIntent.RecordShoot) },
        icon = {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.dasboard_icon_record_shoot_content_description)
            )
        },
        text = { Text(text = stringResource(R.string.dashboard_button_record_shoot_label)) }
    )
}