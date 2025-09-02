package com.jyx.healthsys.controller;

import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.Menu;
import com.jyx.healthsys.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 查询所有菜单数据
     * @return 返回Unification对象，包含查询到的所有菜单数据
     */
    @ApiOperation("查询所有菜单数据")
    @GetMapping
    public Unification<List<Menu>> getAllMenu(){
        List<Menu> menuList = menuService.getAllMenu();
        return Unification.success(menuList);
    }
}

