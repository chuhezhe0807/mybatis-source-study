package com.chuhezhe.sourcestudy;

import com.chuhezhe.sourcestudy.entity.Account;
import com.chuhezhe.sourcestudy.entity.Employee;
import com.chuhezhe.sourcestudy.util.DBUtilBindThread;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.apache.ibatis.type.StringTypeHandler;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    // 使用XMLConfigBuilder解析 mybatis.xml 文件
    @Test
    public void testXMLBuilder() throws IOException {
        InputStream inputStream = new ClassPathResource("mybatis.xml").getInputStream();
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(inputStream);
        Configuration configuration = xmlConfigBuilder.parse();
        logger.info("");
    }

    @Test
    public void testOgnl() throws OgnlException {
        Account account = new Account(1, "xiaozhang", 200);

        Object value = Ognl.getValue("username", account);
        // 静态变量和方法
        Object staticVar = Ognl.getValue("@com.chuhezhe.sourcestudy.util.DBUtilBindThread@THREAD_LOCAL", new Object());
        logger.info("username: {}, staticVar: {}", value, staticVar);
    }

    @Test
    public void testTypeHandler() {
        Connection connection = DBUtilBindThread.getConnection();
        String sql = "select `username`, `money` from `account`";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            // ResultSet.next 移动游标到下一行
            while(resultSet.next()) {
                StringTypeHandler stringTypeHandler = new StringTypeHandler();
                String username = stringTypeHandler.getResult(resultSet, "username");

                IntegerTypeHandler integerTypeHandler = new IntegerTypeHandler();
                Integer money = integerTypeHandler.getResult(resultSet, "money");
                logger.info("username: {}, money: {}", username, money);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // 反射工具类 MetaObject
    // spring框架的ioc为什么使用反射创建对象，而不是用new直接创建一个对象？
    //      1、反射可以不需要将类import就可以创建出对象，如果类发生改动的话，可以不需要重新编译，同样能够work
    //      2、new的方式需要将类引入，且后续类发生变化时，需要重新编译才可以
    @Test
    public void testMetaObject() {
        Employee employee = new Employee(1, "xiaozhang", 2);

        MetaObject metaObject = MetaObject.forObject(
                employee,
                new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory(),
                new DefaultReflectorFactory()
        );
        // 修改当前对象的值
        metaObject.setValue("name", "wang");
        metaObject.setValue("deptId", 4);
        logger.info("employee: {}", employee);
    }


    /**
     * 测试 ErrorContext 类
     * mybatis抛出的错误是如下格式的：
     *
     * Exception in thread "pool-1-thread-10" Exception in thread "pool-1-thread-7" org.apache.ibatis.exceptions.PersistenceException:
     * ### sql has errors.
     * ### The error may exist in user.xml
     * ### The error may involve com.chuhezhe.sourcestudy.ToolTest
     * ### The error occurred while 在第【9】个流程中
     * ### SQL: select ** from `user`
     * ### Cause: java.lang.ArithmeticException: / by zero
     * 。。。错误堆栈
     */
    @Test
    public void testErrorContext() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for(int i = 0; i < 10; i++) {
            final int j = i;

            service.execute(() -> {
                try {
                    ErrorContext.instance()
                            .activity("在第【" + j + "】个流程中")  // 错误位于哪一个流程
                            .object(this.getClass().getName()) // 当前对象
                            .sql("select ** from `user`") // 发生错误的sql语句
                            .resource("user.xml"); // 哪个资源文件发生了错误

                    if(new Random().nextInt(10) > 6) {
                        int m = 1 / 0; // 造一个Runtime异常，查看抛出错误的格式是否是按照上面ErrorContext.instance()设置的
                    }

                    countDownLatch.countDown();
                }
                catch (Exception e) {
                    throw ExceptionFactory.wrapException("sql has errors.", e);
                }
            });
        }

        countDownLatch.await();
    }

    @Test
    public void testMappedStatement() throws IOException {
        InputStream inputStream = new ClassPathResource("mybatis.xml").getInputStream();
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(inputStream);
        Configuration configuration = xmlConfigBuilder.parse();
        SqlSource sqlSource = new StaticSqlSource(configuration, "select `id`, `username`, `money` from `account`");

        MappedStatement.Builder builder = new MappedStatement.Builder(
                configuration,
                "insert",
                sqlSource,
                SqlCommandType.INSERT
        );

        // 设置结果集
        /**
         *
         *     <resultMap id="myResultMap" type="account">
         *         <id property="id" javaType="int" column="id" jdbcType="INTEGER" />
         *         <result property="username" javaType="string" jdbcType="VARCHAR" column="username" />
         *         <!--    未申明的类型会自动映射    -->
         *         <result property="money" column="money" />
         *     </resultMap>
         *
         */
        List<ResultMapping> resultMappings = new ArrayList<>();
        ResultMapping idMapping = new ResultMapping.Builder(configuration, "id", "id", new IntegerTypeHandler()).build();
        ResultMapping usernameMapping = new ResultMapping.Builder(configuration, "username", "username", String.class).build();
        ResultMapping moneyMapping = new ResultMapping.Builder(configuration, "money", "money", Integer.class).build();
        resultMappings.add(idMapping);
        resultMappings.add(usernameMapping);
        resultMappings.add(moneyMapping);

        ResultMap resultMap = new ResultMap.Builder(
                configuration,
                "myResultMap",
                Account.class,
                resultMappings
        ).build();

        builder.resultMaps(List.of(resultMap));
        builder.databaseId("mysql");
        MappedStatement mappedStatement = builder.build();
        BoundSql boundSql = mappedStatement.getBoundSql(new Account(1, null, null));
        String sql = boundSql.getSql();
        logger.info("sql: {}", sql);

        // DynamicContext 是编译解析sql语句的上下文
    }

    @Test
    public void testStringJoiner() {
        StringJoiner stringJoiner = new StringJoiner("-");
        stringJoiner.add("1");
        stringJoiner.add("2");
        stringJoiner.add("3");
        stringJoiner.add("4");
        logger.info("stringJoiner: {}", stringJoiner);
    }

    @Test
    public void testXMLLanguageDriver() throws IOException {
        InputStream inputStream = new ClassPathResource("mybatis.xml").getInputStream();
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(inputStream);
        Configuration configuration = xmlConfigBuilder.parse();

        InputStream inputStream1 = new ClassPathResource("/static/testSql.xml").getInputStream();
        XPathParser xPathParser = new XPathParser(inputStream1);
        XNode xNode = xPathParser.evalNode("/select"); // 解析select标签下的内容

        XMLLanguageDriver xmlLanguageDriver = new XMLLanguageDriver();
        SqlSource sqlSource = xmlLanguageDriver.createSqlSource(configuration, xNode, Account.class);
        logger.info("");
    }

    @Test
    public void testVFS() throws IOException {
        DefaultVFS defaultVFS = new DefaultVFS();

        // 加载classpath下的文件
        List<String> list = defaultVFS.list("com/chuhezhe");
        logger.info("list: {}", list);

        // 加载jar包中的资源
        list = defaultVFS.list(
          new URL("file:///C:/Users/褚涸辙/.m2/repository/mysql/mysql-connector-java/8.0.30/mysql-connector-java-8.0.30.jar"),
                "com/mysql/cj/"
        );
        logger.info("list: {}", list);
    }
}
