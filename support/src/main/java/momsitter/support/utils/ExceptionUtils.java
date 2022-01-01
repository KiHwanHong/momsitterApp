package momsitter.support.utils;

import com.google.common.base.Throwables;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ExceptionUtils {

  private ExceptionUtils() {

  }

  public static String simpleStackTraceString(final Throwable throwable) {
    final String traces = Throwables.getStackTraceAsString(throwable);
    return Arrays.stream(traces.split("\n"))
        .filter(ExceptionUtils::interestedTrace)
        .collect(Collectors.joining("\n"));
  }

  private static boolean interestedTrace(final String trace) {
    return (!trace.contains("\tat") && !trace.matches("^\\t\\.\\.\\. [0-9]+ more$"))
        || trace.contains("in.woowa")
        || trace.contains("Caused by: ");
  }
}
