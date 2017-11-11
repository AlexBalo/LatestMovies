package com.balocco.movies.home.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val PATTERN = "MMM dd, yyyy"

class DateToHumanReadableUseCase @Inject constructor() {

    fun execute(date: Date): String {
        val formatter = SimpleDateFormat(PATTERN, Locale.US)
        return formatter.format(date)
    }
}