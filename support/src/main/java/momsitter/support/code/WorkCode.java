package momsitter.support.code;

/** 사용코드 */
public enum WorkCode {

  // 재직상태 코드
  SY02_10("10"),   // 재직
  SY02_13("13"),   // 휴직
  SY02_30("30"),   // 퇴직

  // 증명서 종류
  PA051_0010("0010"),   // 재직증명서
  PA051_0030("0030"),   // 경력증명서
  PA051_0100("0100"),   // 급여명세서
  PA051_0050("0050"),   // 원천징수영수증
  PA051_0060("0060"),   // 갑종근로소득납세필증명
  PA051_0080("0080"),   // 근로소득원천징수부
  PA051_0110("0110"),   // 퇴직금명세서
  PA051_0120("0120"),   // 퇴직소득 원천징수영수증
  PA051_0130("0130"),   // 이직확인신고

  // 증명서 신청용도
  PA050_05("05"),   // 은행제출용
  PA050_01("01"),   // 회사제출용
  PA050_02("02"),   // 학교제출용
  PA050_03("03"),   // 협회제출용
  PA050_04("04"),   // 비자발급용
  PA050_99("99"),  // 기타사유

  // 증명서 발급상태
  PA053_10("10"),   // 신청중
  PA053_20("20"),   // 완료
  PA053_99("99"),   // 다운로드완료
  PA053_30("30"),    // 발급실패 - 이메일문의
  PA053_40("40"),    // 발급실패 - 기타


  // about certificate Select
  ALL("all"),
  APPLY("apply"),
  COMPLETE("complete"),
  FAILED("failed"),

  // about Hide Code TYPE
  // HYPEN
  HYPEN("-"),
  // SLASH
  SLASH("/"),
  // PASSWORDAFTERKEY
  PASSWORDAFTERKEY("2000"),
  // ZERO
  ZERO("0");

  private String code;

  WorkCode(String code) {
    this.code = code;
  }

  public static String getRequestContentCodeValidate(String certificateRequestContent) {
    switch (certificateRequestContent) {
      case "05":
        return PA050_05.code;
      case "01":
        return PA050_01.code;
      case "02":
        return PA050_02.code;
      case "03":
        return PA050_03.code;
      case "04":
        return PA050_04.code;
      case "99":
        return PA050_99.code;
    }
    return "UNMATCH";
  }
  public static String getDocsCodeValidate(String certificateDocsType) {
    switch (certificateDocsType){
      case "0010":
        return PA051_0010.code;
      case "0030":
        return PA051_0030.code;
      case "0100":
        return PA051_0100.code;
      case "0050":
        return PA051_0050.code;
      case "0060":
        return PA051_0060.code;
      case "0080":
        return PA051_0080.code;
      case "0110":
        return PA051_0110.code;
      case "0120":
        return PA051_0120.code;
      case "0130":
        return PA051_0130.code;
    }
    return "UNMATCH";
  }

  public static String getDocsCodeValue(WorkCode certificateDocsCode) {
    switch (certificateDocsCode) {
      case PA051_0010:
        return PA051_0010.code;
      case PA051_0030:
        return PA051_0030.code;
      case PA051_0050:
        return PA051_0050.code;
      case PA051_0060:
        return PA051_0060.code;
      case PA051_0080:
        return PA051_0080.code;
      case PA051_0100:
        return PA051_0100.code;
      case PA051_0110:
        return PA051_0110.code;
      case PA051_0120:
        return PA051_0120.code;
      case PA051_0130:
        return PA051_0130.code;
    }
    return "";
  }
  public static String getIssueStatus(WorkCode certificateIssueStatus) {
    switch (certificateIssueStatus) {
      case PA053_10:
        return PA053_10.code;
      case PA053_20:
        return PA053_20.code;
      case PA053_30:
        return PA053_30.code;
      case PA053_40:
        return PA053_40.code;
      case PA053_99:
        return PA053_99.code;
    }
    return "0";
  }

  public static String getWorkStatus(WorkCode workStatusCode) {
    switch (workStatusCode) {
      case SY02_10:
        return SY02_10.code;
      case SY02_30:
        return SY02_30.code;
      case SY02_13:
        return SY02_13.code;
    }
    return "0";
  }

  public static String getWorkCode(WorkCode workCode) {
    switch (workCode) {
      case HYPEN:
        return HYPEN.code;
      case SLASH:
        return SLASH.code;
      case PASSWORDAFTERKEY:
        return PASSWORDAFTERKEY.code;
      case ALL:
        return ALL.code;
      case APPLY:
        return APPLY.code;
      case COMPLETE:
        return COMPLETE.code;
      case FAILED:
        return FAILED.code;
      case ZERO:
        return ZERO.code;
    }
    return "ERROR";
  }
}
