package momsitter.core.dto.members;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.core.entity.members.MembersEntity;

/** 신청시스템 - 증명서 결과 정보 dto */

@Getter
@Setter
@NoArgsConstructor
public class MembersDto {

  public static final String DATE_TIME_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$";
  public static final String HOLIDAY_REGEX = "^\\d{1,2}(\\.\\d{1,2})?$";

  /** 회원번호 */
  @JsonIgnore
  private Long membersNumber;

  /** 이름 */
  @Column(name = "members_name", nullable = false, length = 10)
  private String membersName;

  /** 생년월일 */
  @Pattern(regexp = DATE_TIME_FORMAT)
  @Size(max = 8)
  private String membersBirthDay;

  /** 성별 */
  @Column(name = "members_gender", nullable = false, length = 8)
  private int membersGender;

  /** 아이디 */
  @Column(name = "members_id", nullable = false, length = 10)
  private int membersId;

  /** 비밀번호 */
  @Column(name = "members_password", nullable = false, length = 10)
  private String membersPassword;

  /** 이메일 */
  @Column(name = "members_email", nullable = false, length = 10)
  private String membersEmail;

  /** 아이정보	 */
  @Column(name = "members_baby_info", nullable = false, length = 10)
  private String membersBabyInfo;

  /** 신청내용 */
  @Column(name = "members_application_contents", nullable = false, length = 200)
  private String membersApplicationContents;

  @Builder
  public MembersDto(Long membersNumber, String membersName, String membersBirthDay,
      int membersGender,
      int membersId, String membersPassword, String membersEmail, String membersBabyInfo,
      String membersApplicationContents) {
    this.membersNumber = membersNumber;
    this.membersName = membersName;
    this.membersBirthDay = membersBirthDay;
    this.membersGender = membersGender;
    this.membersId = membersId;
    this.membersPassword = membersPassword;
    this.membersEmail = membersEmail;
    this.membersBabyInfo = membersBabyInfo;
    this.membersApplicationContents = membersApplicationContents;
  }

  // for queryDsl
  public MembersDto(MembersEntity membersEntity) {
    this.membersNumber=membersEntity.getMembersNumber();
    this.membersName = membersEntity.getMembersName();
    this.membersBirthDay = membersEntity.getMembersBirthDay();
    this.membersGender = membersEntity.getMembersGender();
    this.membersId = membersEntity.getMembersId();
    this.membersPassword = membersEntity.getMembersPassword();
    this.membersEmail = membersEntity.getMembersEmail();
    this.membersBabyInfo = membersEntity.getMembersBabyInfo();
    this.membersApplicationContents = membersEntity.getMembersApplicationContents();
  }
}
