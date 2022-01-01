package momsitter.core.dto;

import java.util.List;
import lombok.*;

/** 인사시스템 - 대상자 정보 저장 리턴 Dto */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResponseResultDto {

  /** 데이터 전제 갯수 */
  private int totalCount;

  /** 데이터 저장 성공 갯수 */
  private int successCount;

  /** 데이터 저장 실패 사번 */
  private List<String> failedList;

  @Builder
  public ResponseResultDto(int totalCount, int successCount,
      List<String> failedList) {
    this.totalCount = totalCount;
    this.successCount = successCount;
    this.failedList = failedList;
  }
}
