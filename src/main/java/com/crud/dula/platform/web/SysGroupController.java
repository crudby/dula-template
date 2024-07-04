package com.crud.dula.platform.web;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.constant.SysConstants;
import com.crud.dula.platform.dto.SysGroupDTO;
import com.crud.dula.platform.entity.SysGroup;
import com.crud.dula.platform.entity.table.SysGroupTable;
import com.crud.dula.platform.query.SysGroupQuery;
import com.crud.dula.platform.service.SysGroupService;
import com.crud.dula.platform.vo.SysGroupTreeVO;
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
 * 系统组织API
 *
 * @author crud
 * @date 2024/4/30
 */
@Api(value = "/sys", tags = {"系统组织API"})
@Slf4j
@AllArgsConstructor
@RestController
public class SysGroupController {

    private final SysGroupService sysGroupService;

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 列表数据
     */
    @ApiOperation(value = "列表查询", notes = "列表查询", httpMethod = "GET")
    @GetMapping("/group/list")
    public HttpResult<List<SysGroupTreeVO>> page(SysGroupQuery query) {
        List<SysGroup> list = sysGroupService.list(query);
        List<SysGroupTreeVO> treeVOList = BeanUtil.toList(list, SysGroupTreeVO.class);
        Map<Long, List<SysGroupTreeVO>> childrenMap = treeVOList.stream().collect(Collectors.groupingBy(SysGroupTreeVO::getParentId));
        List<SysGroupTreeVO> secondNodes = treeVOList.stream().peek(item -> {
            if (childrenMap.containsKey(item.getId())) {
                item.setChildren(childrenMap.get(item.getId()));
            }
        }).filter(item -> SysConstants.GROUP_ROOT_ID.equals(item.getParentId())).collect(Collectors.toList());
        return HttpResult.success(secondNodes);
    }

    /**
     * 编码是否可用
     *
     * @param id   id
     * @param code code
     * @return bool
     */
    @ApiOperation(value = "编码是否可用", notes = "编码是否可用", httpMethod = "GET")
    @GetMapping("/group/code-usable")
    public HttpResult<Boolean> nameUsable(Long id, @RequestParam("code") String code) {
        SysGroup one = sysGroupService.queryChain().from(SysGroupTable.SYS_GROUP).where(SysGroupTable.SYS_GROUP.GROUP_CODE.eq(code)).one();
        return HttpResult.success(Objects.isNull(one) || Objects.equals(id, one.getId()));
    }

    /**
     * 新增或更新
     *
     * @param dto dto
     * @return bool
     */
    @ApiOperation(value = "新增或更新", notes = "新增或更新", httpMethod = "POST")
    @PostMapping("/group/save")
    public HttpResult<Boolean> save(@Valid @RequestBody SysGroupDTO dto) {
        if (!nameUsable(dto.getId(), dto.getGroupCode()).getData()) {
            return HttpResult.fail("组织编码不可用");
        }
        sysGroupService.saveOrUpdate(BeanUtil.toBean(dto, SysGroup.class));
        return HttpResult.success(Boolean.TRUE);
    }

}
