package momsitter.core.entity.parents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 부모회원 정보 */

@Entity
@Table(name = "parents")
@Getter
@NoArgsConstructor
public class ParentsEntity {

  /** 회원번호 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "parents_number")
  private Long parentsNumber;

  /** 이름 */
  @Column(name = "parents_name", nullable = false, length = 10)
  private String parentsName;

  /** 생년월일 */
  @Column(name = "parents_birth_day", nullable = false, length = 8)
  private String parentsBirthDay;

  /** 성별 */
  @Column(name = "parents_gender", nullable = false, length = 1)
  private String parentsGender;

  /** 아이디 */
  @Column(name = "parents_id", nullable = false, length = 200)
  private String parentsId;

  /** 비밀번호 */
  @Column(name = "parents_password", nullable = false, length = 255)
  private String parentsPassword;

  /** 이메일 */
  @Column(name = "parents_email", nullable = false, length = 255)
  private String parentsEmail;

  /** 아이정보	 */
  @Column(name = "parents_baby_info", nullable = false, length = 255)
  private String parentsBabyInfo;

  /** 신청내용 */
  @Column(name = "parents_application_contents", nullable = false, columnDefinition = "TEXT")
  private String parentsApplicationContents;

  @Builder
  public ParentsEntity(Long parentsNumber, String parentsName, String parentsBirthDay,
      String parentsGender, String parentsId, String parentsPassword, String parentsEmail,
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
}
