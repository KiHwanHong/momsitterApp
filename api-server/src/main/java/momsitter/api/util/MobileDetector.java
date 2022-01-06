package momsitter.api.util;

import java.util.regex.Pattern;

public class MobileDetector {

  private static final Pattern MOBILE_DEVICES = Pattern.compile(
      "/Mobile|iP(hone|od|ad)|Android|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune/");

  private MobileDetector() {
    throw new UnsupportedOperationException();
  }

  public static boolean isMobileDevice(String ua) {
    return ua != null && (MOBILE_DEVICES.matcher(ua).find());
  }
}
