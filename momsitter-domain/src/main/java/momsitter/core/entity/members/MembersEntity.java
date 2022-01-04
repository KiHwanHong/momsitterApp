package momsitter.core.entity.members;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import momsitter.core.entity.BaseEntity;

/** 시터회원 정보 Entity */

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class MembersEntity extends BaseEntity {
  
  /** 회원번호 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "members_number")
  private Long membersNumber;

  /** 이름 */
  @Column(name = "members_name", nullable = false, length = 10)
  private String membersName;

  /** 생년월일 */
  @Column(name = "members_birth_day", nullable = false, length = 8)
  private String membersBirthDay;

  /** 성별 */
  @Column(name = "members_gender", nullable = false, length = 1)
  private String membersGender;

  /** 아이디 */
  @Column(name = "members_id", nullable = false, length = 200)
  private String membersId;

  /** 비밀번호 */
  @Column(name = "members_password", nullable = false, length = 255)
  private String membersPassword;

  /** 이메일 */
  @Column(name = "members_email", nullable = false, length = 255)
  private String membersEmail;

  /** 아이정보	 */
  @Column(name = "members_care_range_info", nullable = false, length = 255)
  private String membersCareRangeInfo;

  /** 신청내용 */
  @Column(name = "members_application_contents", nullable = false, columnDefinition = "TEXT")
  private String membersApplicationContents;

  @Builder
  public MembersEntity(Long membersNumber, String membersName, String membersBirthDay,
      String membersGender, String membersId, String membersPassword, String membersEmail,
      String membersCareRangeInfo, String membersApplicationContents) {
    this.membersNumber = membersNumber;
    this.membersName = membersName;
    this.membersBirthDay = membersBirthDay;
    this.membersGender = membersGender;
    this.membersId = membersId;
    this.membersPassword = membersPassword;
    this.membersEmail = membersEmail;
    this.membersCareRangeInfo = membersCareRangeInfo;
    this.membersApplicationContents = membersApplicationContents;
  }
}
