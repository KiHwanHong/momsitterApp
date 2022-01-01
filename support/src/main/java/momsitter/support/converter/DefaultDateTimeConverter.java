package momsitter.support.converter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDateTimeConverter {

  public static final LocalDate DATE_2999_12_31 = convertDate("2999-12-31");
  public static final LocalDateTime DATE_TIME_2999_12_31 = convertDateTime("2999-12-31 00:00:00");

  private DefaultDateTimeConverter() {

  }

  public static long convertMillisecond(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return 0;
    }
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static LocalDate convertDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    return LocalDate.parse(date, DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static LocalDate convertExcelDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    return LocalDate.parse(date, DefaultDateTimeFormat.DATE_FORMAT_EXCEL);
  }


  public static LocalDate convertSapDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    return LocalDate.parse(date, DefaultDateTimeFormat.DATE_FORMAT_SAP);
  }

  public static String convertDate(LocalDate localDate, DateTimeFormatter format) {
    if (localDate == null) {
      return null;
    }
    return localDate.format(format);
  }

  public static String convertDate(LocalDate localDate) {
    return convertDate(localDate, DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static LocalTime convertTime(String time) {
    if (time == null) {
      return null;
    }
    return LocalTime.parse(time, DefaultDateTimeFormat.TIME_FORMAT);
  }

  public static String convertTime(LocalTime localTime) {
    if (localTime == null) {
      return null;
    }
    return localTime.format(DefaultDateTimeFormat.TIME_FORMAT);
  }

  public static LocalDateTime convertDateTime(String dateTime) {
    if (dateTime == null) {
      return null;
    }
    return LocalDateTime.parse(dateTime, DefaultDateTimeFormat.DATE_TIME_FORMAT);
  }

  public static String convertDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
    if (dateTime == null) {
      return null;
    }

    return dateTime.format(formatter);
  }

  public static String convertDateTime(LocalDateTime dateTime) {
    return convertDateTime(dateTime, DefaultDateTimeFormat.DATE_TIME_FORMAT);
  }

  public static String convertDateWithDay(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.toLocalDate().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd E", Locale.KOREAN));
  }

  public static String convertDay(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static String convertUploadDateTime(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_TIME_FORMAT_UPLOAD);
  }

  public static String convertSapDate(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_FORMAT_SAP);
  }

  public static String convertMailDate(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_FORMAT_MAIL);
  }

}
