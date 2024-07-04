package com.crud.dula.platform.web;

import com.crud.dula.common.base.BasePage;
import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.dto.SysRoleDTO;
import com.crud.dula.platform.entity.SysMenu;
import com.crud.dula.platform.entity.SysRole;
import com.crud.dula.platform.entity.table.SysRoleTable;
import com.crud.dula.platform.query.SysRoleQuery;
import com.crud.dula.platform.service.SysMenuService;
import com.crud.dula.platform.service.SysRoleMenuService;
import com.crud.dula.platform.service.SysRoleService;
import com.crud.dula.platform.vo.SysRoleVO;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        return HttpResult.success(BeanUtil.toPage(rolePage, SysRoleVO.class));
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
     * @param roleId  roleId
     * @param menuIds menuIds
     * @return bool
     */
    @ApiOperation(value = "角色菜单配置", notes = "角色菜单配置", httpMethod = "POST")
    @PostMapping("/role/menu-config")
    public HttpResult<Boolean> userGroupConfig(@RequestParam("roleId") Long roleId, List<Long> menuIds) {
        if (Objects.nonNull(menuIds) && !menuIds.isEmpty()) {
            List<SysMenu> sysMenus = sysMenuService.listByIds(menuIds);
            if (sysMenus.size() < menuIds.size()) {
                return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "配置菜单不存在");
            }
        }
        sysRoleMenuService.config(roleId, menuIds);
        return HttpResult.success(Boolean.TRUE);
    }
}
