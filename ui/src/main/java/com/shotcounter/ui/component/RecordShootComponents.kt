package com.shotcounter.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shotcounter.ui.R
import com.shotcounter.ui.journey.recordshoot.RecordShootIntent
import java.text.DecimalFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun RecordShootHeading() {
    Text(
        text = stringResource(R.string.record_shoot_title),
        style = MaterialTheme.typography.displayMedium
    )
}
@Composable
fun RecordingShotsHeading() {
    Text(
        text = stringResource(R.string.recording_shots_title),
        style = MaterialTheme.typography.titleMedium
    )
}
@Composable
fun ResultsHeading() {
    Text(
        text = stringResource(R.string.results_shots_title),
        style = MaterialTheme.typography.titleMedium
    )
}
@Composable
fun TotalShotsHeading(shotCount: Int) {
    Text(
        text = stringResource(R.string.recording_shots_count, shotCount),
        style = MaterialTheme.typography.titleSmall
    )
}
@Composable
fun CadenceHeading(cadence: Long) {
    Text(
        text = stringResource(R.string.cadence_label, formatTime(cadence)),
        style = MaterialTheme.typography.titleSmall
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
fun StopShootButton(onClick: (RecordShootIntent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick(RecordShootIntent.StartCountDown) },
        icon = {
            Icon(
                Icons.Filled.Close,
                stringResource(R.string.record_shoot_button_stop_content_description)
            )
        },
        text = { Text(text = stringResource(R.string.record_shoot_button_stop_label)) }
    )
}
@Composable
fun ResultsButton(onClick: (RecordShootIntent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick(RecordShootIntent.StartCountDown) },
        icon = {
            Icon(
                Icons.Filled.Info,
                stringResource(R.string.record_shoot_button_stop_content_description)
            )
        },
        text = { Text(text = stringResource(R.string.record_shoot_button_results_label)) }
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

@Composable
fun ShotTimer(time: Long, onStop: (Long) -> Unit = {}) {
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = formatTime(time),
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        StopShootButton {
            onStop(time)
        }
    }
}

@Composable
fun ShotRecord(shotCount: Int, shotTime: Long) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = (shotCount + 1).toString().plus(". "),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = formatTime(shotTime),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

private fun formatTime(time:Long): String {
    val seconds = ((time / 1000.0) % 60.0)
    val minutes = (TimeUnit.MILLISECONDS.toMinutes(time) % 60).toInt()
    val formatSeconds = DecimalFormat("00.00").format(seconds)

    return String.format(Locale.US, "%02d:$formatSeconds", minutes)
}