package pe.edu.upc.MonolithFoodApplication.config;

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
  @Value("${REDIS_PASSWORD}")
  private String redisPassword;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
      LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
        .useSsl().build();
      return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
    }

    @Bean
    public RedisConfiguration defaultRedisConfig() {
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
      config.setHostName(redisHost);
      config.setPassword(RedisPassword.of(redisPassword));
      return config;
    }



  // // Variables estaticas de Cache
  // @Value("${DEFAULT_CACHE_TIME}")
  // private Integer defaultCacheTime;

  // @Bean
  // public RedisConnectionFactory redisConnectionFactory() {
  //   RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
  //   redisConfig.setHostName(redisHost);
  //   redisConfig.setPort(redisPort);
  //   redisConfig.setPassword(RedisPassword.of(this.redisPassword));
  //   return new LettuceConnectionFactory(redisConfig);
  // }

  // @Bean
  // public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
  //   // Defaukt: 11 años
  //   RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
  //     .entryTtl(Duration.ofHours(defaultCacheTime));
    
  //   return RedisCacheManager.builder(redisConnectionFactory)
  //     .cacheDefaults(defaultCacheConfig)
  //     .build();
  // }

}
