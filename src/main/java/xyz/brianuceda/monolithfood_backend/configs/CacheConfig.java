package xyz.brianuceda.monolithfood_backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Setter
public class CacheConfig {
  // Variables estaticas
  @Value("${REDIS_HOST}")
  private String redisHost;
  @Value("${REDIS_PORT}")
  private Integer redisPort;
  @Value("${REDIS_PASSWORD}")
  private String redisPassword;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
      LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
      return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
    }

    @Bean
    public RedisConfiguration defaultRedisConfig() {
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
      config.setHostName(redisHost);
      config.setPort(redisPort);
      config.setPassword(RedisPassword.of(redisPassword));
      return config;
    }
}
