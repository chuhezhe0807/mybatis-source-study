package com.chuhezhe.sourcestudy;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassName: ToolTest
 * Package: com.chuhezhe.sourcestudy
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/4 23:44
 * @Version 1.0
 */
public class ToolTest {
    public static Logger logger = LoggerFactory.getLogger(ToolTest.class);

    @Test
    public void testXPathParser() throws IOException {
        Resource resource = new ClassPathResource("mybatis.xml");
        // 传入 validation, variables, entityResolver 是为了防止不能解析xml的头文件而出现的报错
        XPathParser parser = new XPathParser(resource.getInputStream(), true, null, new XMLMapperEntityResolver());
        XNode xNode = parser.evalNode("/configuration/properties");
        logger.info("xNode: {}", xNode);
    }

    // 测试mybatis中的configuration，将mybatis.xml中的配置项使用 new Configuration() 的方式实现
    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        // properties
        properties.put("driver", "com.mysql.cj.jdbc.Driver");
        properties.put("url", "jdbc:mysql://localhost:3306/mybatissourcestudy?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC");
        properties.put("username", "root");
        properties.put("password", "root");
        configuration.setVariables(properties);

        // settings
        configuration.setLogImpl(Slf4jImpl.class); // 日志
        configuration.setMapUnderscoreToCamelCase(true); // 下划线转为驼峰命名

        // 别名（指定包下取别名，不使用别名的时候，映射需要所映射的类的全类名路径，使用别名后就不需要了，且别名不区分大小写）
        configuration.getTypeAliasRegistry().registerAliases("com.chuhezhe.sourcestudy.entity");

        // 配置环境
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory(); // transactionManager 事务管理器
        PooledDataSource pooledDataSource = new PooledDataSource(); // 数据源 dataSource
        pooledDataSource.setDriver(configuration.getVariables().getProperty("driver"));
        pooledDataSource.setUrl(configuration.getVariables().getProperty("url"));
        pooledDataSource.setUsername(configuration.getVariables().getProperty("username"));
        pooledDataSource.setPassword(configuration.getVariables().getProperty("password"));

        Environment development = new Environment("development", jdbcTransactionFactory, pooledDataSource);
        configuration.setEnvironment(development);

        // mappers
        // 如果是使用的接口加注解的方式是通过 mapperRegistry.addMappers(String packageName) 的方式
        // 如果是原生的mapper.xml则需要去解析每一个配置的mapper.xml,将里面的sql语句封装成 MappedStatement，然后通过addMappedStatement(MappedStatement ms)添加
        StaticSqlSource staticSqlSource = new StaticSqlSource(
                configuration,
                "insert into account (username, money) values ('jerry', 90);"
        );
        MappedStatement mappedStatement = new MappedStatement.Builder(
                configuration,
                "account.insert",
                staticSqlSource,
                SqlCommandType.INSERT
        ).build();

        configuration.addMappedStatement(mappedStatement);
        logger.info("");
    }
}
