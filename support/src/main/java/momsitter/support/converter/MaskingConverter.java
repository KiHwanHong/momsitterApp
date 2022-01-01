package momsitter.support.converter;

public class MaskingConverter {

  private enum MaskingPattern {
    EMAIL("(?<=.{2})[^@\\n](?=[^@\\n]*?@)|(?:(?<=@.)|(?!^)\\G(?=[^@\\n]*$)).(?=.*\\.)"),
    DESTROY("(?<=.{2})[^@\\n](?=[^@\\n]*?@)");

    String regexp;

    MaskingPattern(String regexp) {
      this.regexp = regexp;
    }

    public String getRegexp() {
      return regexp;
    }
  }

  public static String maskEmail(String email) {
    return email.replaceAll(MaskingPattern.EMAIL.getRegexp(), "*");
  }

  public static String maskEmailForDestroy(final String email) {
    return email.replaceAll(MaskingPattern.DESTROY.getRegexp(), "*");
  }
}
