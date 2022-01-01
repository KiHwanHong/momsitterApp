package momsitter.support.page;

import lombok.*;

@Getter
@Setter
@ToString
public class PageOptions {

  private int pageSize = 20;

  private int pageNumber = 1;

  @Getter(AccessLevel.NONE)
  public static final PageOptions DEFAULT = new PageOptions();
}
