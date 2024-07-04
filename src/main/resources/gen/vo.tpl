#set(entityClassName = table.buildEntityClassName())
package #(packageConfig.basePackage).vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * #(table.getComment()) VO。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
@ApiModel(description = "#(table.getComment())VO")
@Data
public class #(entityClassName)VO  {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 更新时间
     */
     @ApiModelProperty("更新时间")
    private LocalDateTime reviseTime;

#for(column : table.columns)

    #set(comment = javadocConfig.formatColumnComment(column.comment))
    #if(isNotBlank(comment))
    /**
     * #(comment)
     */
    @ApiModelProperty("#(column.comment)")
    #end
    private #(column.propertySimpleType) #(column.property)#if(isNotBlank(column.propertyDefaultValue)) = #(column.propertyDefaultValue)#end;

#end
}
