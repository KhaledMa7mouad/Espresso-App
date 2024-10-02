package com.example.mediaplayedapp

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mediaplayedapp.ui.theme.MediaPlayedAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaPlayedAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MediaPlayedDesign(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun MediaPlayedDesign(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val sound = MediaPlayer.create(context, R.raw.espresso )

        // Handling the back button
        BackHandler {
           sound.release()
           context.getActivityOrNull()?.finish()
        }


        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stop),
                contentDescription = "Stop",
                modifier = modifier
                    .size(80.dp)
                    .clickable {
                        sound.stop()
                        sound.prepare()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                modifier = modifier
                    .size(80.dp)
                    .clickable {
                        sound.start()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "Pause",
                modifier = modifier
                    .size(80.dp)
                    .clickable {
                        sound.pause()
                    }
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun MediaPlayedDesignPreview() {
        MediaPlayedDesign()
    }
}


fun Context.getActivityOrNull(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}