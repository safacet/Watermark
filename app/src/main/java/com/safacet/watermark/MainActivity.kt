package com.safacet.watermark

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.safacet.watermark.ui.theme.WatermarkExampleTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.safacet.watermarklibrary.Watermark
import com.safacet.watermarklibrary.WatermarkPosition
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatermarkExampleTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        var bitmap by rememberSaveable {
            mutableStateOf(BitmapFactory.decodeResource(resources, R.drawable.resource_default))
        }
        var text by rememberSaveable { mutableStateOf("") }
        Column {
            Box(modifier = Modifier.padding(4.dp)) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Default PNG",
                    modifier = Modifier.fillMaxWidth(),
                    alignment = Alignment.Center
                )
            }

            Divider(thickness = 1.dp, color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = { Text("Write something to image") }
            )

            Divider(thickness = 1.dp, color = Color.Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))

            Button(onClick = {
                bitmap = Watermark(this@MainActivity, bitmap, text)
                    .setBackgroundColor(android.graphics.Color.BLACK)
                    .getBitmap()
            },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = "Print Watermark")
            }

        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MainScreen()
    }
}
