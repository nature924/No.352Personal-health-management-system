package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jyx.healthsys.entity.Role;
import com.jyx.healthsys.entity.RoleMenu;
import com.jyx.healthsys.mapper.RoleMapper;
import com.jyx.healthsys.mapper.RoleMenuMapper;
import com.jyx.healthsys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    // 新增角色
    @Override
    @Transactional
    public boolean addRole(Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName, role.getRoleName());
        int count = this.baseMapper.selectCount(wrapper).intValue();
        if (count>0){
            return false;
        }else{
            this.baseMapper.insert(role);
            // 写入角色菜单关系表
            if (role.getMenuIdList() != null) {
                for (Integer menuId : role.getMenuIdList()) {
                    roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
                }
            }
        }
        return true;
    }

    // 根据角色ID查询角色信息
    @Override
    public Role getRoleById(Integer id) {
        // 从角色表中获取角色信息
        Role role = this.baseMapper.selectById(id);
        // 获取角色关联的菜单ID列表
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        // 将菜单ID列表设置到角色对象中
        role.setMenuIdList(menuIdList);
        return role;
    }

    // 更新角色信息
    @Override
    @Transactional
    public void updateRole(Role role) {
        // 修改角色表
        this.baseMapper.updateById(role);
        // 删除原有的权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        roleMenuMapper.delete(wrapper);
        // 新增角色的权限
        if (role.getMenuIdList() != null) {
            for (Integer menuId : role.getMenuIdList()) {
                // 添加角色与菜单关系
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }


    @Override
    @Transactional
    public void deleteRoleById(Integer id) {
        this.baseMapper.deleteById(id);
        // 删除角色的权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        roleMenuMapper.delete(wrapper);
    }
}

