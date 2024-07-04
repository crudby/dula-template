package com.crud.dula.platform.web;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.dto.SysMenuDTO;
import com.crud.dula.platform.entity.SysMenu;
import com.crud.dula.platform.entity.table.SysMenuTable;
import com.crud.dula.platform.query.SysMenuQuery;
import com.crud.dula.platform.service.SysMenuService;
import com.crud.dula.platform.support.SysMenuSupport;
import com.crud.dula.platform.vo.SysMenuTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 系统菜单API
 *
 * @author crud
 * @date 2024/4/30
 */
@Api(value = "/sys", tags = {"系统菜单API"})
@Slf4j
@AllArgsConstructor
@RestController
public class SysMenuController {

    private final SysMenuService sysMenuService;

    private final SysMenuSupport sysMenuSupport;

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 列表数据
     */
    @ApiOperation(value = "列表查询", notes = "列表查询", httpMethod = "GET")
    @GetMapping("/menu/list")
    public HttpResult<List<SysMenuTreeVO>> page(SysMenuQuery query) {
        List<SysMenu> list = sysMenuService.list(query);
        return HttpResult.success(sysMenuSupport.applyTree(list));
    }

    /**
     * 编码是否可用
     *
     * @param id id
     * @param code code
     * @return bool
     */
    @ApiOperation(value = "编码是否可用", notes = "编码是否可用", httpMethod = "GET")
    @GetMapping("/menu/code-usable")
    public HttpResult<Boolean> nameUsable(Long id, @RequestParam("code") String code) {
        SysMenu one = sysMenuService.queryChain().from(SysMenuTable.SYS_MENU).where(SysMenuTable.SYS_MENU.MENU_CODE.eq(code)).one();
        return HttpResult.success(Objects.isNull(one) || Objects.equals(id, one.getId()));
    }

    /**
     * 新增或更新
     *
     * @param dto dto
     * @return bool
     */
    @ApiOperation(value = "新增或更新", notes = "新增或更新", httpMethod = "POST")
    @PostMapping("/menu/save")
    public HttpResult<Boolean> save(@Valid @RequestBody SysMenuDTO dto) {
        if (! nameUsable(dto.getId(), dto.getMenuCode()).getData()){
            return HttpResult.fail("菜单编码不可用");
        }
        sysMenuService.saveOrUpdate(BeanUtil.toBean(dto, SysMenu.class));
        return HttpResult.success(Boolean.TRUE);
    }

}
