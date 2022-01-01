package momsitter.api.config.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import momsitter.support.code.DescriptionCode;

import java.io.IOException;

@JsonComponent
public class DescriptionCodeJsonConverter {

  private DescriptionCodeJsonConverter() {

  }

  private static final String FIELD_CODE = "code";
  private static final String FIELD_TEXT = "text";

  public static class Serializer extends JsonSerializer<DescriptionCode> {

    @Override
    public void serialize(DescriptionCode code, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeStringField(FIELD_CODE, code.getCode());
      jsonGenerator.writeStringField(FIELD_TEXT, code.getDescription());
      jsonGenerator.writeEndObject();
    }
  }

  public static class Deserializer extends JsonDeserializer<Enum> implements
      ContextualDeserializer {

    private Class<Enum> targetClass;

    public Deserializer() {
    }

    public Deserializer(Class<Enum> targetClass) {
      this.targetClass = targetClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
        BeanProperty property) {
      return new Deserializer((Class<Enum>) ctxt.getContextualType().getRawClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      String code = null;
      if (jsonParser.currentToken() == JsonToken.VALUE_STRING) {
        code = jsonParser.getValueAsString();
      } else {
        for (JsonToken jsonToken = jsonParser.nextToken(); jsonToken != JsonToken.END_OBJECT;
            jsonToken = jsonParser.nextToken()) {
          if (jsonToken == JsonToken.FIELD_NAME && FIELD_CODE.equals(jsonParser.getCurrentName())) {
            code = jsonParser.nextTextValue();
          }
        }
      }
      return Enum.valueOf(targetClass, code);
    }
  }

}
