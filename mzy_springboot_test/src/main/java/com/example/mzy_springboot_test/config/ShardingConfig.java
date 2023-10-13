package com.example.mzy_springboot_test.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.ShardingRuleCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShardingConfig {


    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean
    public DataSource buildShardingDataSource() throws SQLException {




        Map<String,DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("druidSource",druidDataSource());



        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new Properties());

    }


}
