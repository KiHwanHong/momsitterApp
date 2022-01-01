package momsitter.api.config.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import momsitter.support.converter.ISODateTimeConverter;

import java.io.IOException;
import java.time.LocalDate;

@JsonComponent
public class ISODateJsonConverter {

  private ISODateJsonConverter() {

  }

  public static class Serializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeString(ISODateTimeConverter.convertDate(localDate));
    }
  }

  public static class Deserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser,
        DeserializationContext deserializationContext) throws IOException {
      return ISODateTimeConverter.convertDate(jsonParser.getText());
    }
  }
}
