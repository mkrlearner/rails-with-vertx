package co.tala.atlas.rails.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralBeans {

    @Bean
    public Gson getGson() {
        return new Gson();
    }
}
