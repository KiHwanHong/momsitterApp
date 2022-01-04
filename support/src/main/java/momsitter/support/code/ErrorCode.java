package momsitter.support.code;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {

  SUCCESS("2000", "OK"),

  /* 1001 ~ 2000 logic error*/
  CODE_NOT_FOUND("1003", "코드를 찾을 수 없음"),
  INVALID_PARAMETER("1004", "유효성 확인 실패"),
  REQUEST_NOT_FOUND("1404", "요청을 찾을 수 없습니다."),

  /* 2001 ~ 3000 validation Error */
  VALIDATION_ID_IS_NULL("2001","회원의 ID는 Null 일 수 없습니다."),
  VALIDATION_PASSWORD_IS_NULL("2002", "회원의 비밀번호는 Null 일 수 없습니다."),




  /* 4001 ~ 5000Business Error */
  USER_NOT_FOUND("4000", "사용자를 찾을 수 없습니다."),
  USER_PASSWORD_UNMATCHED_ERROR("4001", "비밀번호가 일치하지않습니다."),
  USER_SAME_PASSWORD_UPDATE_ERROR("4002", "변경 전/후 비밀번호가 동일합니다."),

  AUTHORIZATION_KEY_UNMATCHED_ERROR("4003", "인증키가 일치하지 않습니다."),
  AUTHORIZATION_KEY_EXPIRED_ERROR("4004", "인증키 사용 기한이 만료되었습니다."),

  /* 9001 ~  system error*/
  SYSTEM_ERROR("9001", "시스템 오류"),
  BAD_REQUEST_ERROR("9002", "부적절한 요청 오류"),
  UNAUTHORIZED_ERROR("9003", "인증 오류"),
  NO_SUCH_ENTITY_ERROR("9004", "존재하지 않는 엔티티 오류"),
  EXTERNAL_API_ERROR("9005", "외부 API 호출 에러 입니다."),
  ENTITY_EXISTS_ERROR("9007", "이미 존재하는 데이터입니다."),
  ENTITY_SAVE_ERROR("9008", "데이터 저장에 실패하였습니다."),
  ENTITY_DELETION_ERROR("9009", "데이터 삭제에 실패하였습니다."),
  TOKEN_EXPIRED_OR_UNUSABLE_ERROR("9014", "토큰의 사용 기한이 만료 되었거나 사용할수 없습니다."),
  TOKEN_UNUSABLE_ERROR("9015", "토큰이 이미 사용되어지거나 재발급되어 사용할수 없습니다."),
  DATA_COUNT_ERROR("9017","데이터 건수가 불일치 합니다. 재확인바랍니다."),
  UNKNOWN_ERROR("9999", "알 수 없는 오류");

  private final String code;
  private final String defaultMessage;

  ErrorCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }

}
