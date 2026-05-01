package com.navishkadarshana.smileygame.config.security.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */


public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField("success", false);
        jsonGenerator.writeObjectField("message", value.getMessage());
        jsonGenerator.writeEndObject();
    }
}
