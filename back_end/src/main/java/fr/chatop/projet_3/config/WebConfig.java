package fr.chatop.projet_3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
      .addResourceLocations("file:C:/Users/josel/Documents/Jose/OepnClassroom BAC +5/PROJET 3/Developpez-le-back-end-en-utilisant-Java-et-Spring_PROJET_2/uploads/");
  }
}
