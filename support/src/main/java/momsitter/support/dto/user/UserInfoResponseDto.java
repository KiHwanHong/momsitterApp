package momsitter.support.dto.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신청시스템 - 대상자 Main Response Result Dto
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserInfoResponseDto extends UserBaseDto {

  /** 대상자 이름 */
  private String userName;

  /** 대상자 퇴직/재직 상태코드 */
  private String userStatusCode;

  /** 대상자 초기 비밀번호 갱신 여부 */
  private Boolean isUpdatedUserPassword;

  /** 대상자 재직기간 1년 미만 퇴직 여부 */
  private Boolean isResignedLessThanOneYear;

  /** 증명서 신청 갯수 */
  private Long certificateApply;

  /** 증명서 7일이내 신청갯수 */
  private Long certificateApplyNew;

  /** 증명서 다운로드 완료 갯수 */
  private Long certificateComplete;

  /** 증명서 7일이내 다운로드 완료 갯수 */
  private Long certificateCompleteNew;

  /** 증명서 실패 갯수 - 이메일문의, 기타 */
  private Long certificateFailed;

  /** 증명서 7일이내 실패 갯수 - 이메일문의, 기타 */
  private Long certificateFailedNew;

  /** 대상자 사용가능한 잔여연차 총 갯수 */
  private String holidayActiveCnt;

  // is Resigned for less than a year

  @Builder
  public UserInfoResponseDto(String userNumber, String userName, String userStatusCode,
      Boolean isUpdatedUserPassword, Boolean isResignedLessThanOneYear, Long certificateApply, Long certificateApplyNew, Long certificateComplete,
      Long certificateCompleteNew, Long certificateFailed, Long certificateFailedNew,
      String holidayActiveCnt) {
    super(userNumber);
    this.userName = userName;
    this.userStatusCode = userStatusCode;
    this.isUpdatedUserPassword = isUpdatedUserPassword;
    this.isResignedLessThanOneYear = isResignedLessThanOneYear;
    this.certificateApply = certificateApply;
    this.certificateApplyNew = certificateApplyNew;
    this.certificateComplete = certificateComplete;
    this.certificateCompleteNew = certificateCompleteNew;
    this.certificateFailed = certificateFailed;
    this.certificateFailedNew = certificateFailedNew;
    this.holidayActiveCnt = holidayActiveCnt;
  }
}
