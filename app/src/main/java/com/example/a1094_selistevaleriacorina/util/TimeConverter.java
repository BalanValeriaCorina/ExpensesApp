package com.example.a1094_selistevaleriacorina.util;

import androidx.room.TypeConverter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeConverter {

        @TypeConverter
        public static Time fromString(String value) {
            try {
                return Time.valueOf(value);
            }
            catch (Exception e) {
                return null;
            }
        }

        @TypeConverter
        public static String fromTime(Time value) {
            if (value == null) {
                return null;
            }
            //metoda format este utilizata pentru conversia Date to String
            return value.toString();
        }

}
