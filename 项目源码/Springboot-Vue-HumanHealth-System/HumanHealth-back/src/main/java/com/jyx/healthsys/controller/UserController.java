package com.jyx.healthsys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.BodyNotes;
import com.jyx.healthsys.entity.SportInfo;
import com.jyx.healthsys.entity.User;
import com.jyx.healthsys.service.IBodyNotesService;
import com.jyx.healthsys.service.IBodyService;
import com.jyx.healthsys.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
//声明此类是一个RestController，即RESTful风格的控制器，控制用户相关的请求。
//是一种设计风格，通过URI来定位资源，并使用HTTP协议中的请求方式（GET、POST、PUT、DELETE等）对资源进行操作
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IBodyService bodyService;

    @Autowired
    private IBodyNotesService bodyNotesService;

    /**
     * 获取所有用户
     * @return 返回用户列表
     */
    @GetMapping("/all")
    public Unification<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Unification.success(list,"查询成功");
    }


    @PostMapping("/login")
    public Unification<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data != null) {
            return Unification.success(data);
        }
        return Unification.fail(20002, "用户名或密码错误");
    }



    @PostMapping("/Wxlogin")
    public Unification<Map<String,Object>> Wxlogin(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data != null) {
            return Unification.success(data);
        }
        return Unification.fail();
    }





    @PostMapping("/register")
    public Unification<Map<String,Object>> register(@RequestBody User register) {
        Map<String,Object> data = userService.register(register);
        if (data.get("success")!= null){
            return Unification.success("注册成功");
        }
        else {
            return Unification.fail(20004,"注册失败，用户名已存在");
        }
    }




    @GetMapping("/info")
    public Unification<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // 根据token获取用户信息
        Map<String,Object> data = userService.getUserInfo(token); // 调用userService的getUserInfo方法，传递token参数，返回一个Map<String,Object>类型的data
        if (data!=null){
            return Unification.success(data); // 如果data不为null，返回成功响应，将data作为响应数据返回
        }
        return Unification.fail(20003,"登录信息有误，请重新登录"); // 如果data为null，返回失败响应，返回错误码和错误信息
    }




    @PostMapping("/logout")
    public Unification<?> logout(@RequestHeader("X-Token")String token){
        userService.logout(token);//将当前用户的登录状态从系统中注销
        return Unification.success();
    }



    /**

     根据查询条件获取用户列表，分页查询

     @param username 查询条件：用户名，可选

     @param phone 查询条件：手机号，可选

     @param pageNo 当前页码

     @param pageSize 页面大小

     @return 返回Unification包装后的用户列表，包含总数和当前页码的用户信息列表
     */
    @GetMapping("/list")
    public Unification<Map<String,Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                       @RequestParam(value = "phone", required = false) String phone,
                                                       @RequestParam("pageNo") Long pageNo,
                                                       @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        Page<User> page = new Page<>(pageNo, pageSize);

        userService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal()); // 用户总数
        data.put("rows", page.getRecords()); // 用户列表
        return Unification.success(data);
    }


    @PostMapping("/add")
    public Unification<?> addUser(@RequestBody User user){
        boolean result = userService.addUser(user);
        if (result) {
            return Unification.success("新增成功");
        } else {
            return Unification.fail("用户名已存在");
        }
    }



    @PutMapping("/update")
    public Unification<?> updateUser(@RequestBody User user){
        user.setPassword(null); // 防止密码被修改，将密码设为null
        userService.updateUser(user);
        return Unification.success("修改成功");
    }


    @GetMapping("/{id}")
    public Unification<User> getUserById(@PathVariable("id") Integer id){
        // 通过用户id调用userService的getUserById方法获取用户信息
        User user = userService.getUserById(id);
        // 将获取到的用户信息封装成Unification类型并返回
        return  Unification.success(user);
    }


    @GetMapping("/getBodyNotes/{id}")
    public Unification<List<BodyNotes>> getBodyNotes(@PathVariable("id") Integer id){
        List<BodyNotes> bodyNotesList = bodyNotesService.getBodyNotes(id);
        if (bodyNotesList == null || bodyNotesList.isEmpty()) { // 判断列表是否为空
            return Unification.fail("没有找到多余的记录");
        }
        return  Unification.success(bodyNotesList);
    }


    @GetMapping("/WxgetBodyNotes/{token}")
    public Unification<Map<String,Object>> WxgetBodyNotes(@PathVariable("token") String token){
        // 根据token获取用户信息
        Map<String,Object> data = userService.WxgetUserId(token);
        Integer userId = Integer.parseInt(data.get("id").toString());
        List<BodyNotes> bodyNotes = bodyNotesService.getBodyNotes(userId);
        data.put("bodyNotes", bodyNotes);
        System.out.println(data);
        if (data != null){
            return Unification.success(data);
        }
        return Unification.fail();
    }




    @DeleteMapping("/{id}")
    public Unification<User> deleteUserById(@PathVariable("id") Integer id){
        userService.deletUserById(id);
        return  Unification.success("删除成功");
    }



    @PostMapping("/BodyInformation")
    public Unification<?> BodyInfomationUp(@RequestBody Body body){
        boolean result = bodyService.insert(body);
        if(result == true){
            return Unification.success("上传成功");
        }else{
            return Unification.success("更新成功");
        }
    }



    @PostMapping("/BodyInformationNotes")
    public Unification<?> BodyInformationNotes(@RequestBody BodyNotes bodyNotes){
        bodyNotesService.insert(bodyNotes);
        return Unification.success();
    }



    @GetMapping("/getUserId")
    public Unification<Map<String, Object>> getUserId() {
        Map<String, Object> data = userService.getUserId();
        System.out.println("id"+data);
        if (data != null) {
            return Unification.success(data);
        } else {
            return Unification.fail("用户id获取失败");
        }
    }




    @GetMapping("/getBodyInfo")
    public Unification<Map<String, Object>> getBodyInfo() {
        Map<String, Object> data = userService.getBodyInfo();
        if (data != null) {
            return Unification.success(data);
        } else {
            return Unification.fail(20002);
        }
    }





    @GetMapping("/getBodyList")
    public Unification<Map<String,Object>> getBodyList(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "id", required = false) String id,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<Body> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(name), Body::getName, name);
        wrapper.eq(StringUtils.hasLength(id), Body::getId, id);
        Page<Body> page = new Page<>(pageNo, pageSize); // 构建分页对象，指定页码和每页大小

        bodyService.page(page, wrapper); // 调用userService的分页查询方法，查询指定页码、每页大小和查询条件的用户列表
        Map<String, Object> data = new HashMap<>();

        data.put("total", page.getTotal()); // 将查询到的用户总数放入响应数据中
        data.put("rows", page.getRecords()); // 将查询到的用户列表放入响应数据中
        return Unification.success(data);
    }





    @GetMapping("/getBodyById/{id}")
    public Unification<Body> getBodyById(@PathVariable("id") Integer id){
        // 通过用户id调用userService的getUserById方法获取用户信息
        Body body = bodyService.getBodyById(id);
        // 将获取到的用户信息封装成Unification类型并返回
        return  Unification.success(body);
    }



    @RequestMapping("/updateBody")
    public Unification<?> updateBody(@RequestBody Body body){
        bodyService.updateBody(body);
        return Unification.success("修改成功");
    }


    @DeleteMapping("/deleteBodyById/{id}")
    public Unification<SportInfo> deleteBodyById(@PathVariable("id") Integer id){
        bodyService.deletBodyById(id);
        bodyNotesService.delete(id);
        return  Unification.success("删除成功");
    }





    @PutMapping("/changePassword")
    public Unification<?> changePassword(@RequestBody User user){
        if (userService.updateuser(user)){
            return Unification.success("修改成功，本次已为您登陆，下次登陆请用您的新密码");
        }
        return Unification.fail("修改失败，用户名或密码错误");
    }












    @GetMapping("/getUserBodyList")
    public Unification<Map<String, Object>> getUserBodyList(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<BodyNotes> wrapper = new LambdaQueryWrapper<>();
        Map<String, Object> userid = userService.getUserId();

        if (userid.get("id") != null) {
            wrapper.eq(BodyNotes::getId, userid.get("id"));
        } else {
            // 如果userid.get("id")为null，则返回一个空的查询条件
            wrapper.isNull(BodyNotes::getId);
        }

        Page<BodyNotes> page = new Page<>(pageNo, pageSize); // 构建分页对象，指定页码和每页大小
        bodyNotesService.page(page, wrapper); // 调用userService的分页查询方法，查询指定页码、每页大小和查询条件的用户列表

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal()); // 将查询到的用户总数放入响应数据中
        data.put("rows", page.getRecords()); // 将查询到的用户列表放入响应数据中
        return Unification.success(data);
    }


    @GetMapping("/getUserBodyById/{notesid}")
    public Unification<BodyNotes> getUserBodyById(@PathVariable("notesid") Integer notesid){
        System.out.println(notesid);
        BodyNotes bodyNotes = bodyNotesService.getUserBodyById(notesid);
        return  Unification.success(bodyNotes);
    }

    @RequestMapping("/updateUserBody")
    public Unification<?> updateUserBody(@RequestBody BodyNotes bodyNotes){
        bodyNotesService.updateUserBody(bodyNotes);
        return Unification.success("修改成功");
    }


    @DeleteMapping("/deleteUserBodyById/{notesid}")
    public Unification<SportInfo> deleteUserBodyById(@PathVariable("notesid") Integer notesid){
        bodyNotesService.deleteUserBodyById(notesid);
        return  Unification.success("删除成功");
    }



}
