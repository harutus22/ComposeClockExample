package com.example.composeclockexample

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomClock(
    modifier: Modifier = Modifier,
    clockStyle: ClockStyle = ClockStyle(),
    onTimeChange: (Int, Int, Int, Boolean) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val radius = constraints.maxWidth / 4

        var time by remember {
            mutableLongStateOf(System.currentTimeMillis() / 1000)
        }

        LaunchedEffect(key1 = time) {
            delay(1000)
            time++
        }

        val hour = (((time / 60 / 60) % 12 + 4).toInt()).toFloat()
        val minutes = (time / 60 % 60).toInt()
        val sec = (time % 60).toInt()
        Canvas(modifier = Modifier.size(radius.pxToDp() * 2f)) {


            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    center.x,
                    center.y,
                    radius.toFloat(),
                    Paint().apply {
                        style = Paint.Style.FILL
                        color= Color.WHITE
                        setShadowLayer(
                            60f,
                            0f,
                            0f,
                            Color.argb(50, 0, 0, 0)
                        )
                    }
                )
            }
            for(i in 0..59){
                val angleInRad = (i) * (360f / 60f) * (PI.toFloat() / 180f) //we divide on 60f as we have totally 60 minutes/lines
                val lineType = when(i % 5) {
                     0 -> LineType.LongLine
                    else -> LineType.ShortLine
                }
                val lineLength = when(lineType){
                    LineType.ShortLine -> clockStyle.shortLineLength.toPx()
                    LineType.LongLine -> clockStyle.longLineLength.toPx()
                }
                val lineWidth = when(lineType){
                    LineType.ShortLine -> clockStyle.shortLineWidth
                    LineType.LongLine -> clockStyle.longLineWidth
                }

                val lineStart = Offset(
                    x = (radius - lineLength) * cos(angleInRad) + center.x ,
                    y = (radius - lineLength) * sin(angleInRad) + center.y
                )

                val lineEnd = Offset(
                    x = radius * cos(angleInRad) + center.x,
                    y = radius * sin(angleInRad) + center.y
                )

                drawLine(
                    color = androidx.compose.ui.graphics.Color.Black,
                    start = lineStart,
                    end = lineEnd,
                    strokeWidth = lineWidth.toPx()
                )
            }
            rotate(degrees = sec * (360f / 60f)){
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Red,
                    start = center,
                    end = Offset(center.x, 20.dp.toPx()),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            rotate(degrees = minutes * (360f / 60f)){
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Black,
                    start = center,
                    end = Offset(center.x, 20.dp.toPx()),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            rotate(degrees = hour * (360f / 12f)){
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Black,
                    start = center,
                    end = Offset(center.x, 35.dp.toPx()),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }