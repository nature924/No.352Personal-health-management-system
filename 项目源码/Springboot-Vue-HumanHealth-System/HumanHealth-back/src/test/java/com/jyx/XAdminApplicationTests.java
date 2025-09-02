package com.jyx;

import com.jyx.healthsys.entity.User;
import com.jyx.healthsys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> System.out.println(user + "\n"));
    }

}
