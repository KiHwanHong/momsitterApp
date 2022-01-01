package momsitter.support.utils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.springframework.beans.BeanUtils;

public class ObjectCopyUtils {

  private ObjectCopyUtils() {
    throw new UnsupportedOperationException();
  }

  public static <S, D> D copyNewObject(final S source, final Class<D> destinationType) {
    return copyNewObject(source, destinationType, new String[0]);
  }

  public static <S, D> D copyNewObject(final S source, final Class<D> destinationType,
      final String... ignoredProperties) {
    D instance = BeanUtils.instantiateClass(destinationType);

    copyObject(source, instance, ignoredProperties);

    return instance;
  }

  public static <S, D> D copyObject(final S source, final D destination) {
    return copyObject(source, destination, new String[0]);
  }

  public static <S, D> D copyObject(final S source, final D destination,
      final String... ignoredProperties) {
    ObjectMapper objectMapper = createObjectMapper();
    objectMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
    FilterProvider filters
        = new SimpleFilterProvider().addFilter(
        "customPropertiesNameFilter",
        SimpleBeanPropertyFilter.serializeAllExcept(ignoredProperties));

    try {
      String json = objectMapper.writer(filters).writeValueAsString(source);
      return objectMapper.readerForUpdating(destination).readValue(json);
    } catch (IOException ioe) {
      throw new IllegalArgumentException(ioe);
    }
  }

  public static <S, D> D copyNewObjectNonNull(final S source,
      final Class<D> destinationType,
      final String... keepDefaultValueProperties) {
    ObjectMapper objectMapper = createObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    JsonInclude.Value nonNull = JsonInclude.Value.construct(Include.NON_NULL, Include.NON_NULL);
    objectMapper.setDefaultPropertyInclusion(nonNull);

    objectMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
    FilterProvider filters
        = new SimpleFilterProvider().addFilter(
        "customPropertiesNameFilter",
        SimpleBeanPropertyFilter.serializeAllExcept(keepDefaultValueProperties));

    try {
      D dtoInstance = BeanUtils.instantiateClass(destinationType);
      String json = objectMapper.writer(filters).writeValueAsString(source);
      return objectMapper.readerForUpdating(dtoInstance).readValue(json);
    } catch (IOException ioe) {
      throw new IllegalArgumentException(ioe);
    }
  }

  @JsonFilter("customPropertiesNameFilter")
  private static class PropertyFilterMixIn {
    /* nothing to do */
  }

  private static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    objectMapper.setConfig(
        objectMapper.getSerializationConfig().withAttribute("in-copyProcessing", true)
    );

    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return objectMapper;
  }

}
