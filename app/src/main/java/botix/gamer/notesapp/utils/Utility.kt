package botix.gamer.notesapp.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Utility {

    companion object{
        const val requiredPasswordLength = 8
        const val statusActive = "1"
        const val format = "yyyy-MM-dd HH:mm:ss"
        fun statusActive(): String {
            return statusActive
        }
        fun <T> errorResult(message: String,errorBody: ResponseBody? = null): Result<T> {
            //Timber.d(message)
            var mess_d = message
            if (errorBody != null) {
                val json = JSONObject(errorBody?.string())
                mess_d = json.getString("error")
            }
            return Result.Error(mess_d)
        }

        fun getCurrentDateTimeUtc(format: String): String {
            //DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            return DateTimeFormatter
                .ofPattern(format)
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        }


        /*fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
            val parser = SimpleDateFormat(dateFormat)
            parser.timeZone = timeZone
            return parser.parse(this)
        }

        fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
            val formatter = SimpleDateFormat(dateFormat)
            formatter.timeZone = timeZone
            return formatter.format(this)
        }*/

        fun parseDateTimeUtc(dateTimeUtc: String, format: String): String {
            val datetime = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneOffset.UTC)
                .parse(dateTimeUtc)
            return DateTimeFormatter
                .ofPattern(format)
                .withZone(ZoneOffset.systemDefault())
                .format(datetime)
        }

        fun parseDateTimeLocalToUtc(dateTimeLocal: String, format: String): String {
            val datetime = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneOffset.systemDefault())
                .parse(dateTimeLocal)
            return DateTimeFormatter
                .ofPattern(format)
                .withZone(ZoneOffset.UTC)
                .format(datetime)
        }
    }
}