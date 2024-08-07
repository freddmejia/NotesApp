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
            var newFormat = format
            if (newFormat == null)
                newFormat = Utility.format

            //DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            return DateTimeFormatter
                .ofPattern(newFormat)
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        }


        fun parseDateTimeUtcToLocalTime(dateTimeUtc: String, format: String): String {
            var dateTimeNowUtc = dateTimeUtc
            var newFormat = format

            if (newFormat.isNullOrEmpty())
                newFormat = Utility.format

            if (dateTimeNowUtc.isNullOrEmpty())
                dateTimeNowUtc = getCurrentDateTimeUtc(newFormat)


            val datetime = DateTimeFormatter.ofPattern(newFormat)
                .withZone(ZoneOffset.UTC)
                .parse(dateTimeNowUtc)
            return DateTimeFormatter
                .ofPattern(newFormat)
                .withZone(ZoneOffset.systemDefault())
                .format(datetime)
        }

        fun parseDateTimeLocalToUtc(dateTimeLocal: String, format: String): String {
            var dateTimeLocalC = dateTimeLocal
            var newFormat = format

            if (newFormat.isNullOrEmpty())
                newFormat = Utility.format

            if (dateTimeLocalC.isNullOrEmpty())
                dateTimeLocalC = getCurrentDateTimeUtc(newFormat)


            val datetime = DateTimeFormatter.ofPattern(newFormat)
                .withZone(ZoneOffset.systemDefault())
                .parse(dateTimeLocalC)
            return DateTimeFormatter
                .ofPattern(newFormat)
                .withZone(ZoneOffset.UTC)
                .format(datetime)
        }
    }
}