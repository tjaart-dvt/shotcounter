package com.shotcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shotcounter.ui.navigation.Navigation
import com.shotcounter.ui.theme.ShotCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShotCounterTheme {
                Navigation()
            }
        }
    }
}