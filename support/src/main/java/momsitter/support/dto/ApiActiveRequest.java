package momsitter.support.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiActiveRequest<T> {


  public static final String DATE_TIME_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$";

  @NotNull
  private int totalCount;

  @Pattern(regexp = DATE_TIME_FORMAT)
  @NotEmpty
  private String sendDate;

  private T data;

  @Builder
  public ApiActiveRequest(int totalCount, String sendDate, T data) {
    this.totalCount = totalCount;
    this.sendDate = sendDate;
    this.data = data;
  }
}
