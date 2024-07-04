#set(entityClassName = table.buildEntityClassName())
package #(packageConfig.basePackage).dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * #(table.getComment()) DTO。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
@ApiModel(description = "#(table.getComment())DTO")
@Data
public class #(entityClassName)DTO  {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

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
