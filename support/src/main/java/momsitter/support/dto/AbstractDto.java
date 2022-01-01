package momsitter.support.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import momsitter.support.utils.ObjectCopyUtils;

public abstract class AbstractDto implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String[] ignoredProperties = {"hashCode", "createUser", "createDate", "updateUser",
      "updateDate"};

  public <T> T createNewEntity(final Class<T> entityType) {
    return createNewEntity(entityType, ignoredProperties);
  }

  public <T> T createNewEntity(final Class<T> entityType, final String... ignoredProperties) {
    T entityInstance = BeanUtils.instantiateClass(entityType);

    copyToEntity(entityInstance, ignoredProperties);

    return entityInstance;
  }

  public <T> T copyToEntity(final T entity) {
    return copyToEntity(entity, ignoredProperties);
  }

  /**
   * Dto를 Entity로 복제한다. deep-copy를 수행한다.
   *
   * @param entity            데이터를 입력받을 entity
   * @param ignoredProperties 복제 시 무시할 property name들
   * @param <T>               입력 entity의 타입
   * @return 값이 복제된 entity가 리턴된다.
   */
  public <T> T copyToEntity(final T entity, final String... ignoredProperties) {
    return ObjectCopyUtils.copyObject(this, entity, ignoredProperties);
  }

  @JsonFilter("customPropertiesNameFilter")
  private static class PropertyFilterMixIn {
    /* nothing to do */
  }

  public static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();

    //DTO 에 의도적으로 뺀 메소드에 의한 에러 방지
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    objectMapper.setConfig(
        objectMapper.getSerializationConfig().withAttribute("in-copyProcessing", true)
    );

    //LocalDateTime 의 objectMapper 동작을 가능하게 하기 위해
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return objectMapper;
  }

  /**
   * Entity를 Dto로 복제한다. deep-copy를 수행한다.
   *
   * @param type   복제될 DTO 타입
   * @param entity 원본 entity
   * @param <T>    복제될 DTO 타입
   * @return T 타입의 DTO가 복제되어 리턴된다.
   */
  public static <T extends AbstractDto> T fromEntity(final Class<T> type, final Object entity) {
    return ObjectCopyUtils.copyNewObject(entity, type);
  }

  /**
   * Page&lt;Entity&gt; 정보가 Page&lt;Dto&gt; 정보로 변환된다. 조회된 데이터의 순서를 보존하기 위해 sequential하게 처리하도록 한다.
   *
   * @param dtoType 변환 대상 DTO Type
   * @param page    Entity 목록과 페이징 정보가 포함되어 있는 PageImpl 인스턴스
   * @param <T>     변환 대상 DTO Type
   * @return 변환된 DTO 정보를 포함하고 있는 PageImpl 인스턴스.
   */
  public static <T extends AbstractDto> Page<T> fromPageEntities(final Class<T> dtoType,
      final Page<?> page) {
    List<T> dtoList = fromListEntities(dtoType, page.getContent());

    return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
  }


  public static <T extends AbstractDto> List<T> fromListEntities(final Class<T> dtoType,
      final List<?> entities) {
    return entities.stream().sequential()
        .map(entity -> AbstractDto.fromEntity(dtoType, entity))
        .collect(Collectors.toList());
  }

  /**
   * Dto 의 null 값을 default 값으로 변환하여 복제한다. deep-copy를 수행한다.
   *
   * @param dtoType                    변환 대상 DTO Type
   * @param keepDefaultValueProperties 디폴트 값을 유지할 프로퍼티
   * @return 값이 복제된 entity가 리턴된다.
   */
  public <T> T createNewDto(final Class<T> dtoType, String... keepDefaultValueProperties) {
    return ObjectCopyUtils.copyNewObjectNonNull(this, dtoType, keepDefaultValueProperties);
  }

  public <T> T mergeDto(final T updatedDto, final String... ignoredProperties) {
    return this.mergeDto(updatedDto, true, ignoredProperties);
  }

  public <T> T mergeDtoExcludeChild(final T updatedDto, final String... ignoredProperties) {
    return this.mergeDto(updatedDto, false, ignoredProperties);
  }

  /**
   * Dto 를 병합한다. performChild 여부에 따라 deep-copy 를 수행한다.
   * <p>
   * performChild == true 일 경우, 하위 Object, Array 까지 병합된다. 하위 Array 는 순서대로 병합되며, Array 를 구성하는 데이터의 병합
   * 순서를 보장하고 싶을 경우 매핑을 원할 경우 equals 를 통해 검증이 필요하다. ex) boolean equals = dto.equals(updatedDto);
   * <p>
   *
   * @param updatedDto        업데이트된 정보를 가지고 있는 dto
   * @param performChild      하위 요소 머지 여부
   * @param ignoredProperties 제외할 프로퍼티
   * @param <T>               클래스 타입
   * @return 병합된 dto
   */
  @SuppressWarnings("unchecked")
  private <T> T mergeDto(
      final T updatedDto,
      boolean performChild,
      final String... ignoredProperties) {
    ObjectMapper toBeUpdatedMapper = createObjectMapper();
    ObjectMapper updatedMapper = createObjectMapper();

    updatedMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
    FilterProvider filters
        = new SimpleFilterProvider().addFilter(
        "customPropertiesNameFilter",
        SimpleBeanPropertyFilter.serializeAllExcept(ignoredProperties));

    try {
      JsonNode toBeUpdatedNode = toBeUpdatedMapper.readTree(
          toBeUpdatedMapper.writeValueAsString(this)
      );

      JsonNode updatedNode = updatedMapper.readTree(
          updatedMapper.writer(filters).writeValueAsString(updatedDto)
      );
      JsonNode mergedNode = merge(toBeUpdatedNode, updatedNode, performChild);

      return (T) toBeUpdatedMapper.treeToValue(mergedNode, updatedDto.getClass());
    } catch (IOException ioe) {
      throw new IllegalArgumentException(ioe);
    }
  }

  private static JsonNode merge(JsonNode toBeUpdateNode, JsonNode updatedNode,
      boolean performChild) {

    Iterator<String> fieldNames = updatedNode.fieldNames();
    while (fieldNames.hasNext()) {
      mergeJsonNode(toBeUpdateNode, updatedNode, fieldNames.next(), performChild);
    }
    return toBeUpdateNode;
  }

  private static void mergeJsonNode(JsonNode toBeUpdateNode, JsonNode updatedNode,
      String updatedFieldName, boolean performChild) {
    JsonNode valueToBeUpdate = toBeUpdateNode.get(updatedFieldName);
    JsonNode updatedValue = updatedNode.get(updatedFieldName);

    if (valueToBeUpdate != null && valueToBeUpdate.isArray() && updatedValue.isArray()) {
      // If the node is an ArrayNode
      if (!performChild) {
        return;
      }
      mergeArrayJsonNode(valueToBeUpdate, updatedValue);

    } else if (valueToBeUpdate != null && valueToBeUpdate.isObject()) {
      if (!performChild) {
        return;
      }
      // if the Node is an @ObjectNode
      merge(valueToBeUpdate, updatedValue, true);

    } else if (toBeUpdateNode instanceof ObjectNode) {
      ((ObjectNode) toBeUpdateNode).replace(updatedFieldName, updatedValue);

    } else {
      // CODEREVIEW if-elseif 구문에서 else를 빼면 안 됨. 근데 이 상황이 발생하지 않나?
      throw new UnsupportedOperationException();
    }
  }

  private static void mergeArrayJsonNode(JsonNode valueToBeUpdate, JsonNode updatedValue) {
    // running a loop for all elements of the updated ArrayNode
    for (int i = 0; i < updatedValue.size(); i++) {
      JsonNode updatedChildNode = updatedValue.get(i);

      // Create a new Node in the node that should be updated, if there was no corresponding node in it
      // Use-case - where the updateNode will have a new element in its Array
      if (valueToBeUpdate.size() <= i) {
        ((ArrayNode) valueToBeUpdate).add(updatedChildNode);
      }
      // getting reference for the node to be updated
      JsonNode childNodeToBeUpdated = valueToBeUpdate.get(i);
      merge(childNodeToBeUpdated, updatedChildNode, true);
    }
  }

  public void cleanDatetime() {
    DateTimeCleaner cleaner = new DateTimeCleaner(this);
    cleaner.clean();
  }

  public static void cleanDateTimeFromList(final List<? extends AbstractDto> list) {
    list.forEach(AbstractDto::cleanDatetime);
  }

}
