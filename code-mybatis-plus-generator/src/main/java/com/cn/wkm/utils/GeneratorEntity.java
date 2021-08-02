package com.cn.wkm.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cn.wkm.base.constant.Constant;
import org.apache.tomcat.util.buf.StringUtils;

import java.io.File;
import java.util.*;


/**
 * The type Generator entity.
 *
 * @ClassName GeneratorEntity
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021 /7/31 17:38
 * @ModifyDate 2021 /7/31 17:38
 */
public class GeneratorEntity {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //排除不生成的字段
        String[] superEntityColumns = {"id", "update_by", "update_at", "create_by", "create_at","is_delete"};
        generatorEntityCfg("com.cn.wkm.base.entity.BasePage", superEntityColumns,
                "/templates/entity.page.java.ftl","com.cn.wkm.model.param");
    }

    /**
     * Generator entity cfg.
     *
     * @param superEntity 父类名
     * @param ftlPath     the ftl path
     * @param packagePath the package path
     */
//生成实体
    public static void generatorEntityCfg(String superEntity,String[] superEntityColumns,String ftlPath,String packagePath){
        List<String> packageName = Arrays.asList(packagePath.split("\\."));
        List<String> parentPackage =  packageName.subList(0,packageName.size()-1);
        String curPackageName = packageName.get(packageName.size()-1);
        AutoGenerator autoGenerator = new AutoGenerator();
        /**
         * 包配置
         */
        PackageConfig pc = new PackageConfig();
        pc.setParent(StringUtils.join(parentPackage,'.'))
                .setEntity(curPackageName);

        /**
         * 全局变量配置
         */
        GlobalConfig gc = new GlobalConfig();

        String outPutDir = Constant.PROJECTPATH + "src/main/java";
        // 输出路径
        gc.setOutputDir(outPutDir) //设置输出路径
                .setFileOverride(true) //第二次生成会把第一次生成的覆盖掉
                .setAuthor(Constant.AUTHOR)
                .setOpen(false)
                .setSwagger2(true)
                .setBaseResultMap(true) //基本结果集合
                .setDateType(DateType.TIME_PACK)
                .setBaseColumnList(true) //设置基本的列
                .setIdType(IdType.AUTO)//设置主键生成策略
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setEntityName("%sPageParam")
        ;


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //使用模板引擎避免xml重复生成
        templateConfig.setMapper(null);
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);

        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        String templateFileName = ftlPath.substring(0,ftlPath.length()-".ftl".length());
        //指定自定义模板
        templateConfig.setEntity(templateFileName);

        /**
         * 自定义模板
         */
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String,Object> objectMap= new HashMap<>();
                objectMap.put("superEntityClassPackage",this.getConfig().getStrategyConfig().getSuperEntityClass());
                this.setMap(objectMap);
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>(); // 配置自定义输出模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(ftlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ packagePath.replace('.','/');
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getEntityName());
                return entityFile;
            }
        });
        config.setFileOutConfigList(focList);

        autoGenerator.setGlobalConfig(gc)
                .setDataSource(BaseCodeGenerator.dataSourceConfig())
                .setStrategy(BaseCodeGenerator.dataTable(superEntity,superEntityColumns))
                .setPackageInfo(pc);
        autoGenerator.setTemplate(templateConfig);
        //指定模板引擎
        autoGenerator.setCfg(config);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

}