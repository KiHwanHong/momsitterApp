package momsitter.support.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

  private static final String Y = "Y";
  private static final String N = "N";

  @Override
  public String convertToDatabaseColumn(Boolean attribute) {
    return (attribute != null && attribute) ? Y : N;
  }

  @Override
  public Boolean convertToEntityAttribute(String s) {
    return Y.equals(s);
  }
}
