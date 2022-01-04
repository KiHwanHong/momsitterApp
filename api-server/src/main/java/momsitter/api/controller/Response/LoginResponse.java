package momsitter.api.controller.Response;

import lombok.Getter;

@Getter
public class LoginResponse {

  private String accessToken;
  protected LoginResponse() {}

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
  public String getAccessToken(){
    return accessToken;
  }
}
