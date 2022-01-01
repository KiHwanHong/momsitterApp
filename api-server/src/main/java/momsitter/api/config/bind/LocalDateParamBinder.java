package momsitter.api.config.bind;

import org.springframework.core.convert.converter.Converter;
import momsitter.support.converter.DefaultDateTimeConverter;

import java.time.LocalDate;

public class LocalDateParamBinder implements Converter<String, LocalDate> {

  @Override
  public LocalDate convert(String date) {
    return DefaultDateTimeConverter.convertDate(date);
  }
}
