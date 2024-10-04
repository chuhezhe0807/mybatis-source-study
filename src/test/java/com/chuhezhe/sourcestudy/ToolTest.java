package com.chuhezhe.sourcestudy;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

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
}
