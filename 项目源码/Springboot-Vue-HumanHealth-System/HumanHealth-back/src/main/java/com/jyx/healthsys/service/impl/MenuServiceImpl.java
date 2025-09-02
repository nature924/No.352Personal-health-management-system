package com.jyx.healthsys.service.impl;

import com.jyx.healthsys.entity.Menu;
import com.jyx.healthsys.mapper.MenuMapper;
import com.jyx.healthsys.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public List<Menu> getAllMenu() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 查询所有一级菜单，其父菜单ID为0
        wrapper.eq(Menu::getParentId,0);
        // 获取所有一级菜单
        List<Menu> menuList = this.list(wrapper);
        // 为一级菜单设置子菜单
        setMenuChildren(menuList);
        return menuList;
    }

    // 递归设置子菜单
    private void setMenuChildren(List<Menu> menuList) {
        if(menuList != null){
            for (Menu menu : menuList) {
                LambdaQueryWrapper<Menu> subWrapper = new LambdaQueryWrapper<>();
                // 查询该菜单下所有的子菜单
                subWrapper.eq(Menu::getParentId,menu.getMenuId());
                List<Menu> subMenuList = this.list(subWrapper);
                // 为该菜单设置子菜单
                menu.setChildren(subMenuList);
                setMenuChildren(subMenuList);
            }
        }
    }

    // 根据用户ID获取菜单列表
    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        // 查询该用户能访问的所有一级菜单
        List<Menu> menuList = this.baseMapper.getMenuListByUserId(userId, 0);
        // 为该用户能访问的所有一级菜单设置子菜单
        setMenuChildrenByUserId(userId, menuList);
        // 返回该用户能访问的所有菜单
        return menuList;
    }

    // 递归设置用户能访问的子菜单
    private void setMenuChildrenByUserId(Integer userId, List<Menu> menuList) {
        // 如果菜单列表不为空
        if(menuList != null){
            // 遍历所有菜单
            for (Menu menu : menuList) {
                // 查询该用户能访问的该菜单下所有的子菜单
                List<Menu> subMenuList = this.baseMapper.getMenuListByUserId(userId, menu.getMenuId());
                // 为该菜单设置子菜单
                menu.setChildren(subMenuList);
                // 递归设置子菜单
                setMenuChildrenByUserId(userId,subMenuList);
            }
        }
    }
}

