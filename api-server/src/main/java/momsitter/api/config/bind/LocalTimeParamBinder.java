package momsitter.api.config.bind;

import org.springframework.core.convert.converter.Converter;
import momsitter.support.converter.DefaultDateTimeConverter;

import java.time.LocalTime;

public class LocalTimeParamBinder implements Converter<String, LocalTime> {

  @Override
  public LocalTime convert(String time) {
    return DefaultDateTimeConverter.convertTime(time);
  }

}
