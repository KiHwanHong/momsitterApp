package momsitter.support.utils;

import static java.time.LocalTime.*;
import static java.time.temporal.ChronoUnit.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class DateUtils {

  private DateUtils() {
    throw new UnsupportedOperationException();
  }

  public static boolean isNowDateBeforeAndEquals(LocalDateTime targetDateTime) {
    return LocalDate.now().isBefore(targetDateTime.toLocalDate())
        || LocalDate.now().equals(targetDateTime.toLocalDate());
  }

  public static boolean isNowDateBeforeAndEquals(LocalDate targetDate) {
    return LocalDate.now().isBefore(targetDate)
        || LocalDate.now().equals(targetDate);
  }

  public static boolean isNowDateAfterAndEquals(LocalDateTime targetDateTime) {
    return LocalDate.now().isAfter(targetDateTime.toLocalDate())
        || LocalDate.now().equals(targetDateTime.toLocalDate());
  }

  public static long getTotalNumberOfMonthFromDateRange(final LocalDate from, final LocalDate to) {
    return getTotalNumberOfMonthFromDateRanges(List.of(Pair.of(from, to)));
  }

  public static long getTotalNumberOfMonthFromDateRanges(
      final Collection<Pair<LocalDate, LocalDate>> dateRanges) {
    // 윤년을(365.2425 Days) 고려한 평균 1달 만큼의 초 환산.
    final var monthSeconds = MONTHS.getDuration().get(SECONDS);
    final var totalSeconds = dateRanges.stream()
        .mapToLong(range -> SECONDS.between(
            LocalDateTime.of(range.getLeft(), MIN),
            LocalDateTime.of(range.getRight(), MAX))
        ).sum();

    return Math.abs(totalSeconds / monthSeconds);
  }

  public static String convertNumberOfMonthToYearAndMonthString(final int numberOfMonth) {
    final var year = Math.abs(numberOfMonth / 12);
    final var month = numberOfMonth - (year * 12);

    return String.format(
        "%s년 %s개월",
        StringUtils.leftPad(Integer.toString(year), 2, "0"),
        StringUtils.leftPad(Integer.toString(month), 2, "0")
    );
  }

  /**
   * ex: 2020-12-25(금)
   *
   * @param localDate date
   * @return YYYY-MM-dd(E)
   */
  public static String convertLocalDateToKorDayWeek(LocalDate localDate) {
    if (localDate == null) {
      return "";
    }
    return localDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd(E)", Locale.KOREA));
  }

  public static String convertLocalDateTimeToKorDayWeek(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return "";
    }
    return localDateTime
        .format(DateTimeFormatter.ofPattern("YYYY-MM-dd(E) HH:mm", Locale.KOREA));
  }

  public static String convertLocalDateTimeToKorDayWeek(String localDateTime) {
    if (StringUtils.isBlank(localDateTime)) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return convertLocalDateTimeToKorDayWeek(LocalDateTime.parse(localDateTime, formatter));
  }
}
