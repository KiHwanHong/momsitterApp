package momsitter.support.utils;

import java.time.LocalDate;
import java.time.Period;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

public class ObjectUtils {

  private static final String DEFAULT_EMPTY_STRING_VALUE = "";
  private static final String HTML_TAGS_REGEX = "<[a-zA-Z/][^>]*>";

  private ObjectUtils() {
    throw new UnsupportedOperationException();
  }

  public static String nullSafeToString(final Object obj) {
    return nullSafeToString(obj, DEFAULT_EMPTY_STRING_VALUE);
  }

  public static String nullSafeToString(final Object obj, final String defaultValue) {
    return obj == null ? defaultValue : obj.toString();
  }

  public static Integer nullSafeToInteger(final Object obj) {
    return nullSafeToInteger(obj, 0);
  }

  public static Integer nullSafeToInteger(final Object obj, final Integer defaultValue) {
    try {
      return obj == null ? defaultValue : Integer.parseInt(obj.toString());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  @Nullable
  public static Integer calculateInternationalAge(@Nullable final LocalDate birthday) {
    if (birthday == null) {
      return null;
    }

    return Period.between(birthday, LocalDate.now()).getYears();
  }

  public static String removeHTMLTags(final String text) {
    return StringUtils.removeAll(text, HTML_TAGS_REGEX);
  }

}
