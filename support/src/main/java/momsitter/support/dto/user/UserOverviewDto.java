package momsitter.support.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class UserOverviewDto extends UserBaseDto {

  @JsonInclude(Include.NON_NULL)
  private String userName;

  @JsonInclude(Include.NON_NULL)
  private String userPassword;

  @JsonInclude(Include.NON_NULL)
  private String userPasswordSettingDate;

  @JsonInclude(Include.NON_NULL)
  private String userMobileNumber;

  @JsonInclude(Include.NON_NULL)
  private String userOrganizationName;

  @JsonInclude(Include.NON_NULL)
  private String userEnterDay;

  @JsonInclude(Include.NON_NULL)
  private String userBirthDay;

  @JsonInclude(Include.NON_NULL)
  private String userResignationDay;

  @JsonInclude(Include.NON_NULL)
  private String userWorkStatus;

  @JsonInclude(Include.NON_NULL)
  private String userSendDate;

  @Builder
  public UserOverviewDto(String userNumber, String userName, String userPassword,
      String userPasswordSettingDate, String userMobileNumber, String userOrganizationName,
      String userEnterDay, String userBirthDay, String userResignationDay,
      String userWorkStatus, String userSendDate) {
    super(userNumber);
    this.userName = userName;
    this.userPassword = userPassword;
    this.userPasswordSettingDate = userPasswordSettingDate;
    this.userMobileNumber = userMobileNumber;
    this.userOrganizationName = userOrganizationName;
    this.userEnterDay = userEnterDay;
    this.userBirthDay = userBirthDay;
    this.userResignationDay = userResignationDay;
    this.userWorkStatus = userWorkStatus;
    this.userSendDate = userSendDate;
  }

  @JsonIgnore
  public Boolean isMatchedUserPassword(String userPassword) {
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if(userPassword == null || this.getUserPassword() == null) return null;
    return passwordEncoder.matches(userPassword, this.getUserPassword());
  }
}
