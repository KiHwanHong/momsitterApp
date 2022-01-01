package momsitter.api.config.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import momsitter.support.converter.DefaultDateTimeConverter;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonComponent
public class LocalDateTimeJsonConverter {

  private LocalDateTimeJsonConverter() {

  }

  public static class Serializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeString(DefaultDateTimeConverter.convertDateTime(dateTime));
    }
  }

  public static class Deserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
        DeserializationContext deserializationContext) throws IOException {
      return DefaultDateTimeConverter.convertDateTime(jsonParser.getText());
    }
  }
}
