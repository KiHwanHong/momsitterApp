package momsitter.support.list;

import java.beans.FeatureDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.util.CollectionUtils;
import momsitter.support.utils.ObjectCopyUtils;

public class ListHelper {

  private ListHelper() {
    throw new UnsupportedOperationException();
  }

  public static <S, D, R> List<R> filter(
      final List<S> sourceList,
      final List<D> compairingList,
      final Function<S, R> sourceConversionFunction,
      final Function<D, R> comparingConversionFunction,
      boolean containing) {

    if (sourceList == null
        || sourceList.isEmpty()) {
      return new ArrayList<>();
    }

    Stream<R> convertedSource = sourceList.stream().map(sourceConversionFunction);

    if (compairingList == null) {
      return convertedSource.collect(Collectors.toList());
    }

    return convertedSource.filter(r ->
        containing == compairingList.stream()
            .map(comparingConversionFunction)
            .collect(Collectors.toList()).contains(r))
        .collect(Collectors.toList());

  }

  public static <T> Predicate<T> distinctByKey(
      final Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  /**
   * source의 정보를 target으로 복사한다. source의 데이터 중 null인 데이터는 복사하지 않는다.
   * <p>
   * 성능 향상을 위해 내부적으로 source key base로 grouping한 Map을 사용한다. source key와 target key는 동일해야 한다. 즉,
   * O(n^2)이나 O(nlog(n)) 대신 O(n)의 성능을 보장한다.
   *
   * @param sourceList        소스 데이터 목록
   * @param targetList        복사 대상 데이터 목록
   * @param sourceKeySelector 소스 데이터에서 key를 추출하는 function
   * @param targetKeySelector 복사 대상 데이터에서 key를 추출하는 function
   * @param <S>               소스 객체 타입
   * @param <T>               복사 대상 객체 타입
   * @param <K>               소스 객체와 복사 대상 객체의 Key 타입
   * @return 정보가 복사된 T 타입 목록. 목록(List) 인스턴스 자체는 새로 생성되지만 내부 Element는 원래 레퍼런스를 이용한다. 즉, Element들에 대한
   * 불변성을 보장하지 않는다.
   */
  public static <S, T, K> List<T> copy(
      @Nonnull final List<S> sourceList,
      @Nonnull final List<T> targetList,
      @Nonnull final Function<S, K> sourceKeySelector,
      @Nonnull final Function<T, K> targetKeySelector) {

    Map<K, List<S>> sourceMap = sourceList.stream().collect(
        Collectors.groupingBy(sourceKeySelector)
    );

    return targetList.stream()
        .filter(target ->
            sourceMap.get(targetKeySelector.apply(target)) != null
        )
        .map(target -> {
          sourceMap.get(targetKeySelector.apply(target)).stream()
              .findAny()
              .ifPresent(
                  source -> ObjectCopyUtils.copyObject(source, target, getNullFieldNames(source))
              );

          return target;
        })
        .collect(Collectors.toList());
  }

  public static <T, R> List<R> transform(
      List<T> source,
      final Function<T, R> extractor) {
    if (CollectionUtils.isEmpty(source)) {
      return new ArrayList<>();
    }

    return source.stream().map(extractor).collect(Collectors.toList());
  }

  public static <T> PagedListHolder<T> getPagedListHolder(
      final Collection<T> source, final int pageSize) {
    final var pagedListHolder = new PagedListHolder<T>();

    pagedListHolder.setPageSize(pageSize);
    pagedListHolder.setSource(new ArrayList<>(source));

    return pagedListHolder;
  }

  private static <S> String[] getNullFieldNames(@Nonnull final S source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }
}
