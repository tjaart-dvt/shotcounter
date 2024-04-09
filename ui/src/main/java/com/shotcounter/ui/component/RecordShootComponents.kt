package com.shotcounter.ui.component

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
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
import com.shotcounter.ui.journey.recordshoot.RecordShootState
import com.shotcounter.ui.journey.recordshoot.launchAudioPermission
import java.text.DecimalFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun StartShoot(
    context: Context,
    audioPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    intent: (RecordShootIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.1f))
        RecordShootHeading()
        Spacer(modifier = Modifier.weight(0.1f))
        StartShootButton {
            launchAudioPermission(context, audioPermissionLauncher) {
                intent(RecordShootIntent.StartCountDown)
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun ShotResults(state: RecordShootState.Results) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultsHeading()
        TotalShotsHeading(state.shotTimesMs.size)
        CadenceHeading(state.cadence)
        Spacer(modifier = Modifier.weight(0.1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            state.shotTimesMs.forEachIndexed { index, shotTime ->
                ShotRecord(index, shotTime)
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun ShotsRecorder(
    state: RecordShootState.Record,
    intent: (RecordShootIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordingShotsHeading()
        TotalShotsHeading(state.shotTimesMs.size)
        Spacer(modifier = Modifier.weight(0.1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            state.shotTimesMs.forEachIndexed { index, shotTime ->
                ShotRecord(index, shotTime)
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))
        AnimatedVisibility(
            visible = state.isTimerRunning,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ShotTimer(state.time) {
                intent(RecordShootIntent.EndShoot())
            }
        }
        AnimatedVisibility(
            visible = !state.isTimerRunning,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ResultsButton {
                intent(RecordShootIntent.EndShoot())
            }
        }
    }
}

@Composable
fun CountDownAnimation(intent: (RecordShootIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        CountDown {
            intent(RecordShootIntent.StartShoot)
        }
    }
}

@Composable
fun RecordShootHeading() {
    Text(
        text = stringResource(R.string.record_shoot_title),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.displayMedium
    )
}
@Composable
fun RecordingShotsHeading() {
    Text(
        text = stringResource(R.string.recording_shots_title),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleMedium
    )
}
@Composable
fun ResultsHeading() {
    Text(
        text = stringResource(R.string.results_shots_title),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleMedium
    )
}
@Composable
fun TotalShotsHeading(shotCount: Int) {
    Text(
        text = stringResource(R.string.recording_shots_count, shotCount),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleSmall
    )
}
@Composable
fun CadenceHeading(cadence: Long) {
    Text(
        text = stringResource(R.string.cadence_label, formatTime(cadence)),
        color = MaterialTheme.colorScheme.primary,
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