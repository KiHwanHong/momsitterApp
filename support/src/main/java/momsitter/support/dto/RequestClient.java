package momsitter.support.dto;

import lombok.*;

/**
 * 접속 클라이언트 정보 모델.
 */
@Setter
@Getter
@NoArgsConstructor
public class RequestClient {

  public static final String REQUEST_CLIENT_DATA_REQUEST_ATTRIBUTE = "_requestClientData__";

  /**
   * 원격지 IPv4 주소.
   */
  private String remoteAddress;

  /**
   * 원격지 브라우져 User-Agent String.
   */
  private String clientAgentString;


  @Builder
  public RequestClient(String remoteAddress, String clientAgentString) {
    this.remoteAddress = remoteAddress;
    this.clientAgentString = clientAgentString;
  }
}
