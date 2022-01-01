package momsitter.support.dto.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationKeyDto extends AuthorizationBaseDto {

  private String keyValue;
  private LocalDateTime keyIssueDate;
  private LocalDateTime keyExpiredDate;

  @Builder
  public AuthorizationKeyDto(String keyUserNumber, String keyValue,
      LocalDateTime keyIssueDate, LocalDateTime keyExpiredDate) {
    super(keyUserNumber);
    this.keyValue = keyValue;
    this.keyIssueDate = keyIssueDate;
    this.keyExpiredDate = keyExpiredDate;
  }

  @JsonIgnore
  public Boolean isMatchedKeyValue(String keyValue) {
    if(keyValue == null || this.getKeyValue() == null) return null;
    return StringUtils.equals(keyValue, this.getKeyValue());
  }

  @JsonIgnore
  public Boolean isExpiredKeyValue() {
    if(this.getKeyExpiredDate() == null) return null;
    return LocalDateTime.now().isAfter(this.getKeyExpiredDate());
  }
}

