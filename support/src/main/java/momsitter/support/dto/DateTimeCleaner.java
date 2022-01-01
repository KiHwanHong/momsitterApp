package momsitter.support.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * abstractDto의 LocalDateTime 필드나 LocalDate 필드의 year 값이 2999인 경우 null로 변경한다. 자식 필드로 AbstractDto나
 * List&lt;AbstractDto&gt;가 있다면 동일한 조건으로 cleaning한다. Array / Set / Map 등 List 외의 컬렉션에 대해서는 처리하지
 * 않는다.
 */
public class DateTimeCleaner {

  private final AbstractDto abstractDto;

  public DateTimeCleaner(AbstractDto abstractDto) {
    this.abstractDto = abstractDto;
  }

  /**
   * abstractDto와 상위 클래스의 모든 필드를 조회하여 LocalDateTime 필드와 LocalDate 필드에 대해서만 datetime을 null로 변경한다. 원본
   * 데이터가 변경되므로 사용에 주의를 요하며, 반드시 최종 Response로 전달되기 전에만 사용되어야 한다.
   */
  public void clean() {
    List<Field> allFields = getAllFieldsRecursively();

    allFields.stream().filter(field ->
        field.getType() == LocalDateTime.class
            || field.getType() == LocalDate.class
            || AbstractDto.class.isAssignableFrom(field.getType())
            || List.class.isAssignableFrom(field.getType())
    ).forEach(this::cleanLocalDate);
  }

  private List<Field> getAllFieldsRecursively() {
    final List<Field> fieldList = new ArrayList<>();
    Class<?> current = abstractDto.getClass();

    while (current != Object.class) {
      Field[] fields = current.getDeclaredFields();
      fieldList.addAll(Arrays.asList(fields));
      current = current.getSuperclass();
    }

    return fieldList;
  }

  private void cleanLocalDate(Field field) {
    if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
      return;
    }

    field.setAccessible(true);
    try {
      Object value = field.get(abstractDto);
      if (value instanceof LocalDate && (((LocalDate) value).getYear() == 2999
          || ((LocalDate) value).getYear() == 1000)) {
        field.set(abstractDto, null);
      } else if (value instanceof LocalDateTime && (((LocalDateTime) value).getYear() == 2999
          || ((LocalDateTime) value).getYear() == 1000)) {
        field.set(abstractDto, null);
      } else if (value instanceof AbstractDto) {
        new DateTimeCleaner((AbstractDto) value).clean();
      } else if (value instanceof List && !((List<?>) value).isEmpty()) {
        List<?> list = (List<?>) value;
        cleanList(list);
      }
    } catch (Exception e) {
      throw new IllegalStateException("cleaning datetime error", e);
    }
  }

  private void cleanList(List<?> list) {
    list.forEach(obj -> {
      if (obj instanceof AbstractDto) {
        new DateTimeCleaner((AbstractDto) obj).clean();
      }
    });
  }
}
