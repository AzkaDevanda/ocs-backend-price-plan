package com.sts.sinorita.config;

//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.io.IOException;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//
//@Configuration
//public class JacksonConfig {
//
//  @Bean
//  public Jackson2ObjectMapperBuilderCustomizer customizer() {
//    return new Jackson2ObjectMapperBuilderCustomizer() {
//      @Override
//      public void customize(Jackson2ObjectMapperBuilder builder) {
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(Integer.class, new StdDeserializer<>(Integer.class) {
//          @Override
//          public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//            String value = p.getText();
//            return (value == null || value.trim().isEmpty()) ? null : Integer.valueOf(value);
//          }
//        });
//        builder.modules(module);
//      }
//    };
//  }
//}
