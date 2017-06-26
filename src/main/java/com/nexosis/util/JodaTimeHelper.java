package com.nexosis.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaTimeHelper {

    private static final DateTimeFormatter isoDtFmt = ISODateTimeFormat.dateHourMinuteSecondMillis().withZoneUTC();

    public static DateTimeFormatter getIsoFormatter() {
        return isoDtFmt;
    }

    static final public DateTime START_OF_TIME = new DateTime(
            0000,
            1,
            1,
            0,
            0,
            0,
            DateTimeZone.UTC
    );

    static final public DateTime END_OF_TIME = new DateTime(
            9999,
            1,
            1,
            0,
            0,
            0,
            DateTimeZone.UTC
    );
    public static DateTime convertAxonDateTimeString(String timestamp) {
        return DateTime.parse(timestamp, DateTimeFormat.forPattern("MM/dd/yyyy H:m:s").withZoneUTC());
    }
}