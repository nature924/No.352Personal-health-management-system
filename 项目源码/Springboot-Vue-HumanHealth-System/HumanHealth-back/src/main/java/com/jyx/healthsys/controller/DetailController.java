package com.jyx.healthsys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.Detail;
import com.jyx.healthsys.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detail")
public class DetailController {


    @Autowired
    private IDetailService detailService;




    @GetMapping("/DetailInfo/{sportName}")
    public Unification<Detail> getDetailInfo(@PathVariable String sportName) {
        List<Detail> detailList = detailService.getDetailInfo(sportName);

        System.out.println(detailList);
        if (detailList == null || detailList.isEmpty()) {
            return Unification.fail("查询结果为空");
        }
        // 如果只查询到一条结果，可以直接返回
        Detail detail = detailList.get(0);
        return Unification.success(detail);
    }



    @GetMapping("/getDetailList")
    public Unification<Map<String,Object>> getDetailList(@RequestParam(value = "sportType", required = false) String sportType,
                                                         @RequestParam("pageNo") Long pageNo,
                                                         @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<Detail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(sportType), Detail::getSportType, sportType); // 如果sportType参数不为空，则添加运动类型查询条件
        Page<Detail> page = new Page<>(pageNo, pageSize); // 构建分页对象，指定页码和每页大小

        detailService.page(page, wrapper); //查询指定页码、每页大小和查询条件的用户列表
        Map<String, Object> data = new HashMap<>();

        data.put("total", page.getTotal()); // 用户总数
        data.put("rows", page.getRecords()); // 用户列表
        System.out.println(data);
        return Unification.success(data); // 返回成功响应和响应数据
    }




    @PostMapping("/addDetail")
    public Unification<?> addDetail(@RequestBody Detail detail) {
        boolean isSuccess = detailService.addDetail(detail);
        if (isSuccess) {
            return Unification.success("新增成功");
        } else {
            return Unification.fail("新增失败，运动类型已存在");
        }
    }



    @PutMapping("/updateDetail")
    public Unification<?> updateDetail(@RequestBody Detail detail){
        detailService.updateDetail(detail);
        return Unification.success("修改成功");
    }


    @GetMapping("/getDetailById/{id}")
    public Unification<Detail> getDetailById(@PathVariable("id") Integer id){
        // 通过用户id调用userService的getUserById方法获取用户信息
        Detail detail = detailService.getDetailById(id);
        // 将获取到的用户信息封装成Unification类型并返回
        return  Unification.success(detail);
    }


    @DeleteMapping("/deleteDetailById/{id}")
    public Unification<Detail> deleteDetailById(@PathVariable("id") Integer id){
        detailService.deletDetailById(id);
        return  Unification.success("删除成功");
    }


}

