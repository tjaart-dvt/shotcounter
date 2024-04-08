package com.shotcounter.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shotcounter.ui.R
import com.shotcounter.ui.journey.recordshoot.RecordShootIntent

@Composable
fun RecordShootHeading() {
    Text(
        text = stringResource(R.string.record_shoot_title),
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
fun StartShootButton(onClick: (RecordShootIntent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick(RecordShootIntent.StartCountDown) },
        icon = {
            Icon(
                Icons.Filled.PlayArrow,
                stringResource(R.string.record_shoot_button_start_content_description)
            )
        },
        text = { Text(text = stringResource(R.string.record_shoot_button_start_label)) }
    )
}

@Composable
fun CountDown(onComplete: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.countdown))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        composition = composition,
        restartOnPlay = true,
        iterations = 1
    )
    if (progress > 0.93f) {
        onComplete()
    }
}