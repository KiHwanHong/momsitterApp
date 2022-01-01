package momsitter.api.config.bind;

import org.springframework.core.convert.converter.Converter;
import momsitter.support.converter.DefaultDateTimeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeParamBinder implements Converter<String, LocalDateTime> {

  @Override
  public LocalDateTime convert(String dateTime) {
    return DefaultDateTimeConverter.convertDateTime(dateTime);
  }
}





