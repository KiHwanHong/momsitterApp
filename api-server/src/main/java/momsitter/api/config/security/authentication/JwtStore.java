package momsitter.api.config.security.authentication;

import lombok.Getter;
import lombok.Setter;

public class JwtStore {

  private volatile static JwtStore jwtStore;

  @Getter @Setter
  private volatile static String accessToken;

  private JwtStore() {}

  public static JwtStore getInstance() {
    if(jwtStore == null) {
      synchronized(JwtStore.class) {
        if(jwtStore == null) {
          jwtStore = new JwtStore();
        }
      }
    }
    return jwtStore;
  }
}
