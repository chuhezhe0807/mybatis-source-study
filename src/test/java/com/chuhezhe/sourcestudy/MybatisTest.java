package com.chuhezhe.sourcestudy;

import com.chuhezhe.sourcestudy.dao.AccountDao;
import com.chuhezhe.sourcestudy.dao.DeptDao;
import com.chuhezhe.sourcestudy.dao.EmployeeDao;
import com.chuhezhe.sourcestudy.dao.UserDao;
import com.chuhezhe.sourcestudy.entity.Account;
import com.chuhezhe.sourcestudy.entity.Dept;
import com.chuhezhe.sourcestudy.entity.Employee;
import com.chuhezhe.sourcestudy.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: MybatisTest
 * Package: com.chuhezhe.sourcestudy
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:02
 * @Version 1.0
 */
public class MybatisTest {
    public static Logger logger = LoggerFactory.getLogger(MybatisTest.class);
    private SqlSession sqlSession;

    @BeforeEach
    public void startup() throws IOException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        ClassPathResource resource = new ClassPathResource("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(resource.getInputStream());

        // 需要制定 autoCommit 为 true，否则需要自己commit
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void testMybatis() {
        try {
            int rows = sqlSession.insert("accountDao.insert", new Account(null, "jerry", 2000));
            sqlSession.commit();
            logger.info("rows: {}", rows);
        }
        catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    // 使用代理方式
    @Test
    public void testProxy() {
        try {
            AccountDao mapper = sqlSession.getMapper(AccountDao.class);
            int rows = mapper.insert(new Account(null, "tom", 110));
            sqlSession.commit();
            logger.info("rows: {}", rows);
        }
        catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBaseQuery() {
        DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
        List<Dept> deptList = deptDao.query();
        logger.info("deptList: {}", deptList);

        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        List<Employee> employeeList = employeeDao.query();
        logger.info("employeeList: {}", employeeList);

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = userDao.query();
        logger.info("userList: {}", userList);
    }
}
