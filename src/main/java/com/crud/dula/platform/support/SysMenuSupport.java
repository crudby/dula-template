package com.crud.dula.platform.support;

import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.constant.SysConstants;
import com.crud.dula.platform.entity.SysMenu;
import com.crud.dula.platform.entity.SysMenuOperate;
import com.crud.dula.platform.entity.table.SysMenuOperateTable;
import com.crud.dula.platform.service.SysMenuOperateService;
import com.crud.dula.platform.vo.SysMenuOperateVO;
import com.crud.dula.platform.vo.SysMenuTreeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2024/5/14 10:26
 */
@Component
@AllArgsConstructor
public class SysMenuSupport {

    private final SysMenuOperateService sysMenuOperateService;

    /**
     * 菜单树
     *
     * @param sysMenuList 菜单列表
     * @return treeList
     */
    public List<SysMenuTreeVO> applyTree(List<SysMenu> sysMenuList) {
        return applyTree(sysMenuList, null);
    }

    /**
     * 菜单树
     *
     * @param sysMenuList  菜单列表
     * @param operatorIds  操作权限id
     * @return treeList
     */
    public List<SysMenuTreeVO> applyTree(List<SysMenu> sysMenuList, List<Long> operatorIds) {
        List<SysMenuTreeVO> treeVOList = BeanUtil.toList(sysMenuList, SysMenuTreeVO.class);
        Map<Long, List<SysMenuTreeVO>> childrenMap = treeVOList.stream().collect(Collectors.groupingBy(SysMenuTreeVO::getParentId));
        // 菜单内操作权限
        List<Long> menuIds = sysMenuList.stream().map(SysMenu::getId).collect(Collectors.toList());
        List<SysMenuOperate> operateList = sysMenuOperateService.queryChain().where(SysMenuOperateTable.SYS_MENU_OPERATE.MENU_ID.in(menuIds)).list();
        if (Objects.nonNull(operateList) && !operateList.isEmpty()) {
            operateList.removeIf(operate -> !operatorIds.contains(operate.getId()));
        }
        Map<Long, List<SysMenuOperateVO>> menuOpMap = BeanUtil.toList(operateList, SysMenuOperateVO.class).stream().collect(Collectors.groupingBy(SysMenuOperateVO::getMenuId));
        return treeVOList.stream().peek(item -> {
            if (childrenMap.containsKey(item.getId())) {
                item.setChildren(childrenMap.get(item.getId()));
            }
            if (menuOpMap.containsKey(item.getId())) {
                item.setMenuOperates(menuOpMap.get(item.getId()));
            }
        }).filter(item -> SysConstants.MENU_ROOT_ID.equals(item.getParentId())).collect(Collectors.toList());
    }

}
