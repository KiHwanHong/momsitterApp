package momsitter.core.dto.parents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import momsitter.core.entity.parents.ParentsEntity;
import momsitter.support.dto.AbstractDto;

/** 인사시스템 - 대상자 연차 정보 Dto */

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParentsDto extends AbstractDto {

  public static final String DATE_TIME_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$";
  public static final String HOLIDAY_REGEX = "^\\d{1,2}(\\.\\d{1,2})?$";

  /** 회원번호 */
  @JsonIgnore
  private Long parentsNumber;

  /** 이름 */
  @NotEmpty
  @Valid
  private String parentsName;

  /** 생년월일 */
  @Pattern(regexp = DATE_TIME_FORMAT)
  @Size(max = 8)
  private String parentsBirthDay;

  /** 성별 */
  private String parentsGender;

  /** 아이디 */
  private int parentsId;

  /** 비밀번호 */
  private String parentsPassword;

  /** 이메일 */
  private String parentsEmail;

  /** 아이정보	 */
  private String parentsBabyInfo;

  /** 신청내용 */
  private String parentsApplicationContents;

  @Builder
  public ParentsDto(Long parentsNumber, String parentsName, String parentsBirthDay,
      String parentsGender, int parentsId, String parentsPassword, String parentsEmail,
      String parentsBabyInfo, String parentsApplicationContents) {
    this.parentsNumber = parentsNumber;
    this.parentsName = parentsName;
    this.parentsBirthDay = parentsBirthDay;
    this.parentsGender = parentsGender;
    this.parentsId = parentsId;
    this.parentsPassword = parentsPassword;
    this.parentsEmail = parentsEmail;
    this.parentsBabyInfo = parentsBabyInfo;
    this.parentsApplicationContents = parentsApplicationContents;
  }

  // for queryDsl
  public ParentsDto(ParentsEntity parentsEntity) {
    this.parentsNumber = parentsEntity.getParentsNumber();
    this.parentsName = parentsEntity.getParentsName();
    this.parentsBirthDay = parentsEntity.getParentsBirthDay();
    this.parentsGender = parentsEntity.getParentsName();
    this.parentsId = parentsEntity.getParentsId();
    this.parentsPassword = parentsEntity.getParentsPassword();
    this.parentsEmail = parentsEntity.getParentsEmail();
    this.parentsBabyInfo = parentsEntity.getParentsBabyInfo();
    this.parentsApplicationContents = parentsEntity.getParentsApplicationContents();
  }
}
