package momsitter.support.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public final class ApplicationContextProvider {

  private static final GenericApplicationContext applicationContext = new GenericApplicationContext();

  private ApplicationContextProvider() {
    throw new UnsupportedOperationException();
  }

  static void initialize(final ApplicationContext ctx) {
    applicationContext.setParent(ctx);
    applicationContext.refresh();
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
