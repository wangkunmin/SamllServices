package ${package.Controller};
<#-- 基础模板 jetbrains://idea/navigate/reference?project=small-services&
path=~\.m2\repository\com\baomidou\mybatis-plus-generator\3.4.1\
mybatis-plus-generator-3.4.1.jar!\templates\controller.java.btl-->

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.wkm.base.api.R;
import java.util.List;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;

/**
* <p>
* ${table.comment!} web控制器
* </p>
*
* @author ${author}
* @since ${date}
* @version v1.0
*/
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
@Slf4j
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
 <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
 <#else>
public class ${table.controllerName} {
 </#if>
 @Autowired
 private ${table.serviceName} ${(table.serviceName)?uncap_first};

 /**
 * 查询分页数据
 */
 @ApiOperation(value = "查询分页数据")
 @RequestMapping(value = "/list")
 public R<Page> findListByPage(@Valid @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
  return success(null);
 }


 /**
 * 根据id查询
 */
 @ApiOperation(value = "根据id查询数据")
 @RequestMapping(value = "/getById")
 public R<${entity}> getById(@RequestParam("pkid") String pkid){
  return success(null);
 }

 /**
 * 新增
 */
 @ApiOperation(value = "新增数据")
 @RequestMapping(value = "/add", method = RequestMethod.POST)
 public R<${entity}> add(@RequestBody ${entity} ${entity?uncap_first}){
  return success(null);
 }

 /**
 * 删除
 */
 @ApiOperation(value = "删除数据")
 @RequestMapping(value = "/del")
 public R<String> delete(@RequestParam("ids") List<String> ids){
    return success(null);
   }

 /**
 * 修改
 */
 @ApiOperation(value = "更新数据")
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 public R<${entity}> update(@RequestBody ${entity} ${entity?uncap_first}){
  return success(null);
 }
}
</#if>