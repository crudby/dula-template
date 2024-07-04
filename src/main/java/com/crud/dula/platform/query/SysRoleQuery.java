package com.crud.dula.platform.query;

import com.crud.dula.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author crud
 * @date 2024/4/19
 */
@ApiModel(description = "角色查询")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQuery extends BaseQuery {

}
