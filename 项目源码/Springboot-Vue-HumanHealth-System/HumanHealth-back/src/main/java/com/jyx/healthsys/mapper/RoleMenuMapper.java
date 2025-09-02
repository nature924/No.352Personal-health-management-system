package com.jyx.healthsys.mapper;

import com.jyx.healthsys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //这个方法是为了根据角色ID获取该角色拥有的菜单ID列表。
    public List<Integer> getMenuIdListByRoleId(Integer roleId);
}
