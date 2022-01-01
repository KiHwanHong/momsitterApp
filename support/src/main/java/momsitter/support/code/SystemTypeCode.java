package momsitter.support.code;

/**
 * 시스템 구분 코드
 */
public enum SystemTypeCode implements DescriptionCode {

  SYSTY000("시스템구분"),
  SYSTY001("API-SERVER"),
  SYSTY002("FRONT-SERVER");

  private final String description;

  SystemTypeCode(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

}
