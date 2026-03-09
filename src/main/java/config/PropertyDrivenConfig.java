// src/main/java/config/PropertyDrivenConfig.java
package config;

import dao.DaoApi;
import dao.DaoFile;
import dao.DaoImpl;
import dao.DaoImpl2;
import dao.IDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:app.properties")
public class PropertyDrivenConfig {

    @Value("${dao.target:dao}")
    private String target;

    @Bean(name = "dao")
    @Primary
    public IDao selectedDao() {
        return switch (target) {
            case "dao" -> new DaoImpl();
            case "dao2" -> new DaoImpl2();
            case "daoFile" -> new DaoFile();
            case "daoApi" -> new DaoApi();
            default -> throw new IllegalArgumentException(
                    "Implémentation inconnue: " + target + " (dao|dao2|daoFile|daoApi)"
            );
        };
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}