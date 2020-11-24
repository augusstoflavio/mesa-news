package br.com.augusto.mesanews.app.data.converter

import android.annotation.SuppressLint
import br.com.augusto.mesanews.app.data.resources.DateResource
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {

        fun toResource(date: Date?): DateResource? {
            if (date == null) {
                return null
            }

            @SuppressLint("SimpleDateFormat")
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            return DateResource(
                date = format.format(date.getTime()),
                timezone = format.getTimeZone().getID()
            )
        }

        fun fromResource(dateResource: DateResource?): Date? {
            if (dateResource == null) {
                return null
            }

            val calendar: Calendar

            @SuppressLint("SimpleDateFormat")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone(dateResource.timezone)

            val date: Date?
            date = sdf.parse(dateResource.date)

            calendar = GregorianCalendar()
            calendar.setTime(date)
            calendar.setTimeZone(TimeZone.getDefault())

            return calendar.time
        }
    }
}