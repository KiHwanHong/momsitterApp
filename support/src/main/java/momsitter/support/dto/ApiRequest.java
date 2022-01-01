package momsitter.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;
import org.springframework.util.CollectionUtils;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiRequest<T> {

  public static final String DATE_TIME_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$";

  @NotNull
  private int totalCount;

  @Pattern(regexp = DATE_TIME_FORMAT)
  @NotEmpty
  private String sendDate;

  @Valid
  private List<T> data;

  @Builder
  public ApiRequest(int totalCount, String sendDate, List<T> data) {
    this.totalCount = totalCount;
    this.sendDate = sendDate;
    this.data = data;
  }

  @JsonIgnore
  public boolean isEqualsTotalCount() {
    int dataCount = CollectionUtils.isEmpty(this.data) ? 0 : this.data.size();
    return this.totalCount == dataCount;
  }
}
