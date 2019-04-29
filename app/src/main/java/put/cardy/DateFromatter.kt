package put.cardy

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DateFromatter {
    companion object {
        fun format(date: DateTime?): String? {
            val fmt = DateTimeFormat.forPattern("d MMMM, yyyy HH:HH")
            return date!!.toString(fmt)
        }

    }
}