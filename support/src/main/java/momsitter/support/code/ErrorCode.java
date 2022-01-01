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

  /* 4000 ~ Business Error */
  USER_NOT_FOUND("4000", "사용자를 찾을 수 없습니다."),
  USER_PASSWORD_UNMATCHED_ERROR("4001", "비밀번호가 일치하지않습니다."),
  USER_SAME_PASSWORD_UPDATE_ERROR("4002", "변경 전/후 비밀번호가 동일합니다."),

  AUTHORIZATION_KEY_UNMATCHED_ERROR("4003", "인증키가 일치하지 않습니다."),
  AUTHORIZATION_KEY_EXPIRED_ERROR("4004", "인증키 사용 기한이 만료되었습니다."),

//  DOCS_APPLY_EXISTS("4005", "발급요청 중인 문서가 있어\n 재신청이 불가능합니다."),
//  DOCS_APPLY_TYPE_ERROR("4006", "요청하신 문서코드는 잘못된 코드입니다."),
//  DOCS_APPLY_NOT_EXISTS("4007", "신청정보가 존재하지 않습니다."),
//  DOCS_APPLY_REQUESTCONTENT_TYPE_ERROR("4008", "요청하신 신청용도 코드는 잘못된 코드입니다."),
//  DOCS_ISSUE_STATUS_ERROR("4009", "발급상태 코드를 확인바랍니다."),
//  DOCS_ISSUE_STATUS_FAILEDREASON_ERROR("4010","발급실패 사유를 확인바랍니다."),
//  DOCS_APPLY_YEAR_ERROR("4011","신청년도 연말정산 대상자가 아닙니다."),
//  DOCS_APPLY_EXIST_TURNOVER("4012", "신청중인 이직확인서가 있습니다."),
//  DOCS_APPLY_RETIREE_YEAR_ERROR("4013","해당문서를 재직자는 신청할 수 없습니다."),
//  DOCS_EXIST_TURNOVER("4014","신청한 이직확인서가 존재합니다."),
//  CERTIFICATE_DOCS_NOT_FILEPATH("4015", "증명서 파일경로 정보를 입력하여주세요."),
//  DOCS_EXIST_DOWNLOAD("4016","다운로드가 가능한 신청서가 있습니다."),
//  CERTIFICATE_REQUEST_SEQ_EMPTY("4017", "증명서 일련번호가 누락되었습니다."),
//  TOKEN_VALUE_ERROR("4018", "토큰 값이 누락되었습니다."),
//  TOKEN_EXPIRED_DATE_ERROR("4019", "토큰 만료일이 누락되었습니다."),
//  NOTICE_BOARD_CONTENTS_COMMENTS("4100", "게시판 상세정보를 확인해주세요."),
//  FAILED_INFO_CODE("4500","잘못된 정보 입니다."),

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
