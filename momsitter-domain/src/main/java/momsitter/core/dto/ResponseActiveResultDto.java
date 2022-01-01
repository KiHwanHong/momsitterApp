package momsitter.core.dto;

import lombok.*;

/** 인사시스템 - 대상자 증명서 발급 리턴 Dto */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResponseActiveResultDto {

  /** 데이터 전제 갯수 */
  private int totalCount;

  /** 데이터 저장 성공 갯수 */
  private String successUser;

  /** 데이터 저장 실패 사번 */
  private String failedUser;

  @Builder
  public ResponseActiveResultDto(int totalCount, String successUser, String failedUser) {
    this.totalCount = totalCount;
    this.successUser = successUser;
    this.failedUser = failedUser;
  }
}
