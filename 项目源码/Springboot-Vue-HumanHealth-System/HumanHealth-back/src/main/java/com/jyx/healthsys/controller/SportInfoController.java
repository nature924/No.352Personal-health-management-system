package com.jyx.healthsys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.ChainQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.*;
import com.jyx.healthsys.service.IDetailService;
import com.jyx.healthsys.service.ISportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sport")
public class SportInfoController {

    @Autowired
    private ISportInfoService sportInfoService;


    //获取所有运动知识
    @GetMapping("/getAllSportInfo")
    public Map<String, Object> getAllSportInfo() {
        List<SportInfo> sportInfos = sportInfoService.getAllSportInfos();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("sportInfos", sportInfos);
        response.put("code", 20000);
        response.put("message", "success");
        response.put("data", data);
        return response;
    }




    @GetMapping("/getSportList")
    public Unification<Map<String,Object>> getSportList(@RequestParam(value = "sportType", required = false) String sportType,
                                                       @RequestParam("pageNo") Long pageNo,
                                                       @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<SportInfo> wrapper = new LambdaQueryWrapper<>(); // 构建查询条件
        wrapper.eq(StringUtils.hasLength(sportType), SportInfo::getSportType, sportType); // 如果sportType参数不为空，则添加运动类型查询条件
        Page<SportInfo> page = new Page<>(pageNo, pageSize); // 构建分页对象，指定页码和每页大小

        sportInfoService.page(page, wrapper); // 调用userService的分页查询方法，查询指定页码、每页大小和查询条件的用户列表
        Map<String, Object> data = new HashMap<>(); // 创建响应数据对象

        data.put("total", page.getTotal()); // 将查询到的用户总数放入响应数据中
        data.put("rows", page.getRecords()); // 将查询到的用户列表放入响应数据中
        return Unification.success(data); // 返回成功响应和响应数据
    }




    @PostMapping("/add")
    public Unification<?> addSport(@RequestBody SportInfo sport) {
        boolean isSuccess = sportInfoService.addSport(sport);
        if (isSuccess) {
            return Unification.success("新增成功");
        } else {
            return Unification.fail("新增失败，运动类型已存在");
        }
    }



    @PutMapping("/update")
    public Unification<?> updateSport(@RequestBody SportInfo sport){
        sportInfoService.updateSport(sport);
        return Unification.success("修改成功");
    }


    @GetMapping("/{id}")
    public Unification<SportInfo> getSportById(@PathVariable("id") Integer id){
        // 通过用户id调用userService的getUserById方法获取用户信息
        SportInfo sportInfo = sportInfoService.getSportById(id);
        // 将获取到的用户信息封装成Unification类型并返回
        return  Unification.success(sportInfo);
    }


    @DeleteMapping("/{id}")
    public Unification<SportInfo> deletSportById(@PathVariable("id") Integer id){
        sportInfoService.deletUserById(id);
        return  Unification.success("删除成功");
    }









}

