package momsitter.support.query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.Query;

public class NativeQueryHelper {

  private NativeQueryHelper() {
    throw new UnsupportedOperationException();
  }

  public static <T> String createNativeInsertQuery(
      final String tableName,
      final List<T> objects,
      final Map<String, Function<T, Object>> columnMapper) {
    String columnNames = String.join(",", columnMapper.keySet());

    String sql = String.format("INSERT INTO %s (%s) VALUES", tableName, columnNames);
    for (int i = 0; i < objects.size(); i++) {
      String delimiter = i > 0 ? ", " : "";

      int index = i;
      sql += String.format(
          delimiter + "(%s)",
          columnMapper.keySet().stream()
              .map(k -> String.format(":%s%s", k, index))
              .collect(Collectors.joining(",")));
    }

    return sql;
  }

  public static <T> void setParameters(
      Query query,
      final List<T> objects,
      final Map<String, Function<T, Object>> columnMapper) {
    for (int i = 0; i < objects.size(); i++) {
      T obj = objects.get(i);

      setParameters(i, query, obj, columnMapper);
    }
  }

  public static <T> void setParameters(
      int index,
      Query query,
      final T obj,
      final Map<String, Function<T, Object>> columnMapper) {
    columnMapper.forEach((key, value) -> query.setParameter(key + index, value.apply(obj)));
  }

}
