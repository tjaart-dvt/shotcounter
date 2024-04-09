package com.shotcounter.ui.journey.recordshoot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.shotcounter.ui.component.CountDownAnimation
import com.shotcounter.ui.component.ShotResults
import com.shotcounter.ui.component.ShotsRecorder
import com.shotcounter.ui.component.StartShoot

@Composable
fun RecordShoot(navController: NavHostController, viewModel: RecordShootViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
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
                StartShoot(context, audioPermissionLauncher, intent)
            }

            RecordShootState.StartCountDown -> {
                CountDownAnimation(intent)
            }

            is RecordShootState.Record -> {
                ShotsRecorder(state, intent)
            }

            is RecordShootState.Results -> {
                ShotResults(state)
            }
        }
    }
}

fun launchAudioPermission(
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