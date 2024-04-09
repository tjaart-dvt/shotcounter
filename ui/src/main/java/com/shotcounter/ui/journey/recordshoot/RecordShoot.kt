package com.shotcounter.ui.journey.recordshoot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.shotcounter.ui.component.CadenceHeading
import com.shotcounter.ui.component.CountDown
import com.shotcounter.ui.component.RecordShootHeading
import com.shotcounter.ui.component.RecordingShotsHeading
import com.shotcounter.ui.component.ResultsButton
import com.shotcounter.ui.component.ResultsHeading
import com.shotcounter.ui.component.ShotRecord
import com.shotcounter.ui.component.ShotTimer
import com.shotcounter.ui.component.StartShootButton
import com.shotcounter.ui.component.TotalShotsHeading
import java.text.DecimalFormat
import java.util.Locale
import java.util.concurrent.TimeUnit


@Composable
fun RecordShoot(navController: NavHostController, viewModel: RecordShootViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StateRenderer(state, viewModel::onIntent)
        }
    }
}

@Composable
private fun StateRenderer(state: RecordShootState, intent: (RecordShootIntent) -> Unit) {
    val context: Context = LocalContext.current

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            intent(RecordShootIntent.StartCountDown)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            RecordShootState.Render -> {
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

            RecordShootState.StartCountDown -> {
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

            is RecordShootState.Record -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecordingShotsHeading()
                    TotalShotsHeading(state.shotTimesMs.size)
                    Spacer(modifier = Modifier.weight(0.1f))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        state.shotTimesMs.forEachIndexed { index, shotTime ->
                            ShotRecord(index, shotTime)
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.1f))
                    if(state.isTimerRunning) {
                        ShotTimer(state.time) {
                            intent(RecordShootIntent.EndShoot())
                        }
                    } else {
                        ResultsButton {
                            intent(RecordShootIntent.EndShoot())
                        }
                    }


                }
            }

            is RecordShootState.Results -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResultsHeading()
                    TotalShotsHeading(state.shotTimesMs.size)
                    CadenceHeading(state.cadence.toLong())
                    Spacer(modifier = Modifier.weight(0.1f))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        state.shotTimesMs.forEachIndexed { index, shotTime ->
                            ShotRecord(index, shotTime)
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }
    }


}

private fun launchAudioPermission(
    context: Context,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onPermissionAvailable: () -> Unit = {}
) {
    val permissionCheckResult =
        ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        onPermissionAvailable()
    } else {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
}