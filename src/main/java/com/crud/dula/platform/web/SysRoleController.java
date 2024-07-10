package com.crud.dula.platform.web;

import com.crud.dula.common.base.BasePage;
import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.dto.RoleMenuDTO;
import com.crud.dula.platform.dto.SysRoleDTO;
import com.crud.dula.platform.entity.SysMenu;
import com.crud.dula.platform.entity.SysRole;
import com.crud.dula.platform.entity.SysRoleMenu;
import com.crud.dula.platform.entity.table.SysRoleMenuTable;
import com.crud.dula.platform.entity.table.SysRoleTable;
import com.crud.dula.platform.entity.table.SysUserRoleTable;
import com.crud.dula.platform.query.SysRoleQuery;
import com.crud.dula.platform.service.SysMenuService;
import com.crud.dula.platform.service.SysRoleMenuService;
import com.crud.dula.platform.service.SysRoleService;
import com.crud.dula.platform.service.SysUserRoleService;
import com.crud.dula.platform.vo.SysRoleVO;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统角色API
 *
 * @author crud
 * @date 2024/4/29
 */
@Api(value = "/sys", tags = {"系统角色API"})
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/sys")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysUserRoleService sysUserRoleService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @param page  分页条件
     * @return 分页结果
     */
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping("/role/page")
    public HttpResult<Page<SysRoleVO>> page(SysRoleQuery query, BasePage page) {
        Page<SysRole> rolePage = sysRoleService.page(page, query);
        List<SysRole> records = rolePage.getRecords();
        if (!records.isEmpty()) {
            Map<Long, List<Long>> roleMenusMap = sysRoleMenuService.list(SysRoleMenuTable.SYS_ROLE_MENU.ROLE_ID.in(records.stream().map(SysRole::getId).collect(Collectors.toList())))
                    .stream().collect(Collectors.groupingBy(SysRoleMenu::getRoleId, Collectors.mapping(SysRoleMenu::getMenuId, Collectors.toList())));
            return HttpResult.success(BeanUtil.toPage(rolePage, SysRoleVO.class, (r, t) -> {
                t.setMenuList(roleMenusMap.get(r.getId()));
            }));
        }
        return HttpResult.success(BeanUtil.toPage(rolePage, SysRoleVO.class));
    }

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @ApiOperation(value = "列表查询", notes = "列表查询", httpMethod = "GET")
    @GetMapping("/role/list")
    public HttpResult<List<SysRoleVO>> list(SysRoleQuery query) {
        List<SysRole> list = sysRoleService.list(query);
        if (!list.isEmpty()) {
            Map<Long, List<Long>> roleMenusMap = sysRoleMenuService.list(SysRoleMenuTable.SYS_ROLE_MENU.ROLE_ID.in(list.stream().map(SysRole::getId).collect(Collectors.toList())))
                    .stream().collect(Collectors.groupingBy(SysRoleMenu::getRoleId, Collectors.mapping(SysRoleMenu::getMenuId, Collectors.toList())));
            return HttpResult.success(BeanUtil.toList(list, SysRoleVO.class, (r, t) -> {
                t.setMenuList(roleMenusMap.get(r.getId()));
            }));
        }
        return HttpResult.success(BeanUtil.toList(list, SysRoleVO.class));
    }

    /**
     * 编码是否可用
     *
     * @param id   id
     * @param code code
     * @return bool
     */
    @ApiOperation(value = "编码是否可用", notes = "编码是否可用", httpMethod = "GET")
    @GetMapping("/role/code-usable")
    public HttpResult<Boolean> nameUsable(Long id, @RequestParam("code") String code) {
        SysRole one = sysRoleService.queryChain().from(SysRoleTable.SYS_ROLE).where(SysRoleTable.SYS_ROLE.ROLE_CODE.eq(code)).one();
        return HttpResult.success(Objects.isNull(one) || Objects.equals(id, one.getId()));
    }

    /**
     * 新增或更新
     *
     * @param dto dto
     * @return bool
     */
    @ApiOperation(value = "新增或更新", notes = "新增或更新", httpMethod = "POST")
    @PostMapping("/role/save")
    public HttpResult<Boolean> save(@Valid @RequestBody SysRoleDTO dto) {
        if (!nameUsable(dto.getId(), dto.getRoleCode()).getData()) {
            return HttpResult.fail("角色编码不可用");
        }
        sysRoleService.saveOrUpdate(BeanUtil.toBean(dto, SysRole.class));
        return HttpResult.success(Boolean.TRUE);
    }

    /**
     * 角色菜单配置
     *
     * @param roleMenuDTO dto
     * @return bool
     */
    @ApiOperation(value = "角色菜单配置", notes = "角色菜单配置", httpMethod = "POST")
    @PostMapping("/role/menu-config")
    public HttpResult<Boolean> roleMenuConfig(@Valid @RequestBody RoleMenuDTO roleMenuDTO) {
        if (Objects.nonNull(roleMenuDTO.getMenuIds()) && !roleMenuDTO.getMenuIds().isEmpty()) {
            List<SysMenu> sysMenus = sysMenuService.listByIds(roleMenuDTO.getMenuIds());
            if (sysMenus.size() < roleMenuDTO.getMenuIds().size()) {
                return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "配置菜单不存在");
            }
        }
        sysRoleMenuService.config(roleMenuDTO.getRoleId(), roleMenuDTO.getMenuIds());
        return HttpResult.success(Boolean.TRUE);
    }

    /**
     * 角色删除
     *
     * @param id id
     * @return bool
     */
    @ApiOperation(value = "角色删除", notes = "角色删除", httpMethod = "POST")
    @PostMapping("/role/delete/{id}")
    public HttpResult<Boolean> delete(@PathVariable("id") Long id) {
        long count = sysUserRoleService.count(SysUserRoleTable.SYS_USER_ROLE.ROLE_ID.eq(id));
        if (count > 0) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.code, "该角色下存在用户，不可删除");
        }
        LogicDeleteManager.execWithoutLogicDelete(() -> {
            sysRoleMenuService.remove(SysRoleMenuTable.SYS_ROLE_MENU.ROLE_ID.eq(id));
            sysRoleService.removeById(id);
        });
        return HttpResult.success(Boolean.TRUE);
    }
}
