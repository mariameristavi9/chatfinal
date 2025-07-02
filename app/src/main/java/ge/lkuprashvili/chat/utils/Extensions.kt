package ge.lkuprashvili.chat.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Long.toTimeFormat(): String {
    val now = System.currentTimeMillis()
    val diff = now - this
    val mins = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)

    return when {
        mins < 60 -> "$mins min ago"
        hours < 24 -> "$hours h ago"
        else -> SimpleDateFormat("d MMM", Locale.getDefault()).format(Date(this))
    }
}
