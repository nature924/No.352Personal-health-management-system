package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jyx.config.JwtConfig;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.Menu;
import com.jyx.healthsys.entity.User;
import com.jyx.healthsys.entity.UserRole;
import com.jyx.healthsys.mapper.UserMapper;
import com.jyx.healthsys.mapper.UserRoleMapper;
import com.jyx.healthsys.service.IBodyService;
import com.jyx.healthsys.service.IMenuService;
import com.jyx.healthsys.service.IUserRoleService;
import com.jyx.healthsys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;



    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private JwtConfig jwtConfig;


    private User loginUser = null;
    @Autowired
    private IBodyService bodyMapper;



        @Override
        public Map<String, Object> login(User user) {
            // 根据用户名和密码查询
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, user.getUsername());
            wrapper.eq(User::getPassword, user.getPassword());
            User loginUser = this.baseMapper.selectOne(wrapper);

            // 如果查询到了用户，则生成Token返回给前端
            if (loginUser != null) {
                // 将用户密码设置为 null，避免密码泄露
                loginUser.setPassword(null);

                String token = jwtConfig.createToken(loginUser); //创建 Token
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                return data;
            }
            return null;
        }



        @Override
        public Map<String, Object> Wxlogin(User user) {
            // 根据用户名和密码查询
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, user.getUsername());
            wrapper.eq(User::getPassword, user.getPassword());
            User loginUser = this.baseMapper.selectOne(wrapper);

            // 如果查询到了用户，则生成Token返回给前端
            if (loginUser != null) {
                // 将用户密码设置为 null，避免密码泄露
                loginUser.setPassword(null);

                String token = jwtConfig.createToken(loginUser); //创建 Token
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                return data;
            }
            return null;
        }



        @Override
        public Map<String, Object> getUserInfo(String token) {
            try {
                // 通过 JWT 解析 token 得到用户信息
               loginUser = jwtConfig.parseToken(token, User.class);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (loginUser != null) {
                // 如果获取到了用户信息，则组装返回数据
                Map<String, Object> data = new HashMap<>();
                data.put("name", loginUser.getUsername());
                data.put("avatar", loginUser.getAvatar());
                data.put("id",loginUser.getId());
                // 获取用户角色列表
                List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
                data.put("roles", roleList);

                // 获取用户菜单列表
                List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
                data.put("menuList", menuList);
                return data;
            }
            return null;
        }


        @Override
        public void logout(String token) {

        }

        @Override
        public boolean addUser(User user) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, user.getUsername());
            int count = this.baseMapper.selectCount(wrapper).intValue();
            if (count>0){
                return false;
            }
            else {
                // 写入用户表
                this.baseMapper.insert(user);
                // 写入用户角色表
                List<Integer> roleIdList = user.getRoleIdList();// 获取用户角色ID列表
                if (roleIdList != null) {
                    for (Integer roleId : roleIdList) {
                        // 将角色ID和用户ID插入到用户角色表中
                        userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
                    }
                }
            }
            return true;

        }

        @Override
        public User getUserById(Integer id) {

            // 根据用户ID查询用户信息
            User user = this.baseMapper.selectById(id);
            System.out.println(user);
            // 根据用户ID查询用户角色列表
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRole::getUserId, id);
            List<UserRole> userRoleList = userRoleMapper.selectList(wrapper); // 从用户角色表中查询出所有用户角色，并赋值给userRoleList变量

            // 将用户角色ID列表设置到用户对象中
            List<Integer> roleIdList = userRoleList.stream() // 将 userRoleList 转化为一个 Stream<UserRole> 对象，使得可以对其中的每一个元素进行操作
                    .map(userRole -> {
                        return userRole.getRoleId();
                    })
                    .collect(Collectors.toList()); // 将每个roleId值收集到一个List<Integer>对象中，并赋值给roleIdList变量
            user.setRoleIdList(roleIdList); // 将roleIdList设置到user对象中的roleIdList属性中

            return user;
        }



        @Override
        @Transactional
        public void updateUser(User user) {
            // 更新用户表
            this.baseMapper.updateById(user);
            // 清除原有的角色
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRole::getUserId, user.getId());
            userRoleMapper.delete(wrapper);
            // 设置新的角色
            List<Integer> roleIdList = user.getRoleIdList(); // 获取用户新的角色 ID 列表
            if (roleIdList != null) {
                for (Integer roleId : roleIdList) {
                    // 设置新角色
                    userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
                }
            }
        }



        @Override
        public void deletUserById(Integer id) {
            // 通过用户ID删除用户
            this.baseMapper.deleteById(id);
            // 清除原有角色
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRole::getUserId, id); // 查询条件：用户ID等于id
            userRoleMapper.delete(wrapper); // 执行删除操作
        }


        @Override
        public Map<String, Object> register(User user) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            Map<String, Object> map = new HashMap<>();
            // 查询用户名是否已存在
            wrapper.eq(User::getUsername, user.getUsername());
            Long count = this.baseMapper.selectCount(wrapper);
            if (count > 0) {
                map.put("fail", false);
                return map;
            } else {
                user.setAvatar("https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
                this.baseMapper.insert(user);
                // 获取插入数据后的ID
                Integer userId = user.getId();
                // 创建UserRole对象，并设置角色
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(3);

                // 插入到数据库中
                boolean result = userRoleService.save(userRole);
                if (result) {
                    map.put("success", true);
                } else {
                    map.put("fail", false);
                }
                return map;
            }
        }




        @Override
        public Map<String, Object> getUserId() {
            Map<String, Object> data = new HashMap<>();
            // 添加键为 "id"，值为当前登录用户的 ID
            if (data != null) {
                data.put("id", loginUser.getId());
                return data;
            }
            return null;
        }


        @Override
        public Map<String, Object> WxgetUserId(String token) {
            User WxloginUser = null;
            try {
                // 通过 JWT 解析 token 得到用户信息
                WxloginUser = jwtConfig.parseToken(token, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (WxloginUser != null) {
                // 如果获取到了用户信息，则组装返回数据
                Map<String, Object> data = new HashMap<>();
                data.put("id",WxloginUser.getId());
                return data;
            }
            return null;
        }





        @Override
        public Map<String, Object> getBodyInfo() {
            Map<String, Object> data = new HashMap<>();
            data.put("id", loginUser.getId());
            Integer pid = (Integer) data.get("id");

            List<Body> bodyList = bodyMapper.getBodyListByUserId(pid);

            Map<String, Object> result = new HashMap<>();
            result.put("bodyList", bodyList);
            return result;
        }

        @Override
        public boolean updateuser(User user) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, user.getUsername())
                    .eq(User::getPassword, user.getPassword());
            // 查询数据库中是否存在匹配的用户
            User oldPassword = this.baseMapper.selectOne(wrapper);
            if (oldPassword != null) {
                // 找到匹配的用户，更新密码
                oldPassword.setPassword(user.getNewPassword());
                this.baseMapper.updateById(oldPassword);
                return true;
            }
            return false;
        }





    }
