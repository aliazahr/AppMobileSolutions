package ca.qc.coursevents

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

import ca.qc.coursevents.ui.theme.CoursEventsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoursEventsTheme {
                ImageGesturesDemo()
            }
        }
    }
}

@Composable
fun ImageGesturesDemo() {
    val context = LocalContext.current

    // Mets au moins 2 images dans res (ex.: drawable/androidparty.png + mipmap/ic_launcher)
    val images = listOf(
        R.drawable.androidbackground,
        R.mipmap.ic_launcher
    )

    var imgIndex by remember { mutableIntStateOf(0) }

    // Position courante (glisser/déplacer)
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(images[imgIndex]),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                // 1) Tap / Double tap / Long press
                .pointerInput(imgIndex) {
                    detectTapGestures(
                        onTap = {
                            Toast.makeText(context, "Tap simple 👆", Toast.LENGTH_SHORT).show()
                        },
                        onDoubleTap = {
                            imgIndex = (imgIndex + 1) % images.size
                        },
                        onLongPress = { _: Offset ->
                            val t = Toast.makeText(context, "menu contextuel", Toast.LENGTH_LONG)
                            t.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 120)
                            t.show()
                        }
                    )
                }
                // 2) Glisser pour déplacer
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                // Application du décalage
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoursEventsTheme {
        ImageGesturesDemo()
    }
}