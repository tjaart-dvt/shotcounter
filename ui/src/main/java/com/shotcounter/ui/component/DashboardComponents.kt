package com.shotcounter.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shotcounter.ui.R
import com.shotcounter.ui.journey.dashboard.DashboardIntent

@Composable
fun DashboardHeading() {
    Text(
        text = stringResource(R.string.dashboard_title),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.displayMedium
    )
}
@Composable
fun DashboardSubHeading() {
    Text(
        text = stringResource(R.string.dashboard_sub_title),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun RecordNewShootButton(onClick: (DashboardIntent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick(DashboardIntent.RecordShoot) },
        icon = {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.dashboard_icon_record_shoot_content_description)
            )
        },
        text = { Text(text = stringResource(R.string.dashboard_button_record_shoot_label)) }
    )
}