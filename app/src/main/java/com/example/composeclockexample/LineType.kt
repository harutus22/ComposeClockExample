package com.example.composeclockexample

sealed class LineType {
    data object ShortLine: LineType()
    data object LongLine: LineType()
}