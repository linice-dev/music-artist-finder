package com.linicedev.music_artist_finder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.MediaType.ALL;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class MusicArtistFinderConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
        objectMapper.setSerializationInclusion(NON_NULL);

        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(ALL));

        List<HttpMessageConverter<?>> messageConverters = List.of(converter);
        return new RestTemplate(messageConverters);
    }
}
