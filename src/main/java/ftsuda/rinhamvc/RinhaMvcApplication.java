package ftsuda.rinhamvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ftsuda.rinhamvc.jpa"})
@EnableRedisRepositories(basePackages = {"ftsuda.rinhamvc.redis"})
public class RinhaMvcApplication {

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    // https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis:support:cache-abstraction
    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    // @Bean
    // @ConfigurationProperties("spring.datasource.hikari")
    // @Primary
    // DataSource dataSource(DataSourceProperties dataSourceProperties){
    // final HikariDataSource dataSource = (HikariDataSource) dataSourceProperties.initializeDataSourceBuilder()
    // .username(dataSourceProperties.getUsername())
    // .password(dataSourceProperties.getPassword())
    // .build();
    // dataSource.setRegisterMbeans(true);
    // return dataSource;
    // }

    public static void main(String[] args) {
        SpringApplication.run(RinhaMvcApplication.class, args);
    }

}
