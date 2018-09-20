package com.husen.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis的配置文件，包括集群和单机模式
 * Created by HuSen on 2018/8/31 11:30.
 */
@Configuration
public class RedisConfig {
    /**是否使用集群模式*/
    private static final boolean CUSTER_ENABLE = false;
    /**
     * @return jedisConnectionFactory
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig  = new JedisPoolConfig();
        //连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(true);
        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间, 或连接数超过最大空闲连接数)
        poolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        //是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(true);
        //默认为pool
        poolConfig.setJmxNamePrefix("pool");
        //是否启用后进先出, 默认true
        poolConfig.setLifo(true);
        //最大空闲连接数, 默认8个
        poolConfig.setMaxIdle(10);
        //最大连接数, 默认8个
        poolConfig.setMaxTotal(50);
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        poolConfig.setMaxWaitMillis(30000);
        //资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除  默认1800000毫秒(30分钟)
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        //最小空闲连接数, 默认0
        poolConfig.setMinIdle(0);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        poolConfig.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        poolConfig.setSoftMinEvictableIdleTimeMillis(1800000);
        //在获取连接的时候检查有效性, 默认false
        poolConfig.setTestOnBorrow(false);
        //在空闲时检查连接有效性, 默认false
        poolConfig.setTestWhileIdle(true);
        //空闲资源的检测周期(单位为毫秒) 如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(-1);
        //在返回连接之前检查有效性 默认false
        poolConfig.setTestOnReturn(false);
        //在创建之前检查pool的有效性 默认false
        poolConfig.setTestOnCreate(false);

        //连接池Client Builder
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder poolingClientConfigurationBuilder = JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig);
        if(CUSTER_ENABLE) {
            //****************创建集群配置****************//
            List<String> nodes = new ArrayList<>();
            nodes.add("192.168.162.132:6379");
            nodes.add("192.168.162.133:6379");
            nodes.add("192.168.162.134:6379");
            nodes.add("192.168.162.135:6379");
            nodes.add("192.168.162.136:6379");
            nodes.add("192.168.162.137:6379");
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(nodes);
            //最大重定向次数
            redisClusterConfiguration.setMaxRedirects(2);
            //创建连接工厂
            return new JedisConnectionFactory(redisClusterConfiguration, poolingClientConfigurationBuilder.build());
        }else {
            //****************创建单节点配置****************//
            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
            //使用数据库1
            standaloneConfiguration.setDatabase(1);
            //创建连接工厂
            return new JedisConnectionFactory(standaloneConfiguration, poolingClientConfigurationBuilder.build());
        }
    }

    /**
     * @param connectionFactory JedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(name = "stringRedisTemplate")
    @Autowired
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory connectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        //开启事务支持
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
}
