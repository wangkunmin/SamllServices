package com.cn.wkm.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cn.wkm.base.constant.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CodeGenerator
 * @Description mybatis-plus 代码自动生成工具
 * @Author kunmin.wang
 * @Date 2021/7/30 17:54
 * @ModifyDate 2021/7/30 17:54
 */
public class BaseCodeGenerator {
    public static String superEntity = "com.cn.wkm.base.entity.BaseEntity";
    public static String[] superEntityColumns ={"id", "update_by", "update_at", "create_by", "create_at","is_delete"};

    public static String superController = "com.cn.wkm.base.api.IController";

    public static String templateType = ".ftl";
    public static String controllerTemplate = "/templates/controller.java";
    public static String serviceTemplate = "/templates/service.java";
    public static String serviceImplTemplate = "/templates/serviceImpl.java";
    public static String mapperXmlTemplate = "/templates/mapper.xml";
    public static String mapperTemplate = "/templates/mapper.java";
    public static String entityTemplate = "/templates/entity.java";



    public static DataSourceConfig dataSourceConfig(){
        /**
         * 数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setDriverName(Constant.DRIVERNAME)
                .setUsername(Constant.USERNAME)
                .setPassword(Constant.PASSWORD)
                .setUrl(Constant.URL);
        return dsc;
    }
    public static GlobalConfig globalConfig(){
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
                .setControllerName("%sController");
        return gc;
    }
    public static StrategyConfig dataTable(String superEntity,String[] superEntityColumns){
        /**
         * 数据库表配置
         */
        StrategyConfig strategy = new StrategyConfig();
        strategy.setSkipView(true) //是否跳过试图 默认false
                .setTablePrefix(Constant.TABLEPREFIX)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel) //数据库表字段映射到实体的命名策略
                .setCapitalMode(true)//设置全局大写命名
                .setInclude(Constant.TABLENAMES) //指定表
                //.setExclude("sequence"); //  不需要生成的表、

        .setEntityTableFieldAnnotationEnable(true); // 生成的字段 是否添加注解，默认false
        //是否支持lombok
        strategy.setEntityLombokModel(true);
        //rest风格
        strategy.setRestControllerStyle(true);
        //下划线命名 user_id_2
        strategy.setControllerMappingHyphenStyle(true);
        //逻辑删除配置
        strategy.setLogicDeleteFieldName("is_delete");

        // 公共父类（基类需已存在）
        strategy.setSuperEntityClass(superEntity);
        strategy.setSuperEntityColumns(superEntityColumns);

        // 公共父类（基类需已存在）
        strategy.setSuperControllerClass(superController);

        //自动填充配置  GMT_MODIFIED

        //创建时间更新时间填充
        TableFill gmt_creat = new TableFill("GMT_CREAT", FieldFill.INSERT);
        TableFill gmt_modified = new TableFill("GMT_MODIFIED", FieldFill.UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmt_creat);
        tableFills.add(gmt_modified);
        strategy.setTableFillList(tableFills);
        //乐观锁
        strategy.setVersionFieldName("version");

        return strategy;
    }

    public static void main(String[] args){
        //用来获取Mybatis-Plus.properties文件的配置信息
        AutoGenerator autoGenerator = new AutoGenerator();
        /**
         * 包配置
         */
        PackageConfig pc = new PackageConfig();
        pc.setParent(Constant.PACKAGENAME)
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setEntity("entity");

        autoGenerator.setGlobalConfig(globalConfig())
                .setDataSource(dataSourceConfig())
                .setStrategy(dataTable(superEntity,superEntityColumns))
                .setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(mapperXmlTemplate); //使用模板引擎，不写则默认会使用mybatis-plus generator中的模板
        templateConfig.setMapper(mapperTemplate);
        templateConfig.setController(controllerTemplate);
        templateConfig.setService(serviceTemplate);
        templateConfig.setServiceImpl(serviceImplTemplate);
        templateConfig.setEntity(entityTemplate);

        autoGenerator.setTemplate(templateConfig);

        //指定模板引擎
        autoGenerator.setCfg(injectionConfig());
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    public static InjectionConfig injectionConfig() {
        /**
         * 自定义模板
         */
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义controller的代码模板
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(controllerTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ Constant.PACKAGENAME.replace(".","/") + "/controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }
        });

        // 自定义service的代码模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(serviceTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ Constant.PACKAGENAME.replace(".","/") + "/service";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceName());
                return entityFile;
            }
        });

        // 自定义service的代码模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(serviceImplTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ Constant.PACKAGENAME.replace(".","/") + "/service/impl";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceImplName());
                return entityFile;
            }
        });

        // 配置自定义输出模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(entityTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ Constant.PACKAGENAME.replace(".","/") + "/entity";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getEntityName());
                return entityFile;
            }
        });

        // 配置自定义输出模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = Constant.PROJECTPATH + "src/main/java/"+ Constant.PACKAGENAME.replace(".","/") + "/mapper";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getMapperName());
                return entityFile;
            }
        });

        // 配置自定义输出模板
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperXmlTemplate+templateType) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                return Constant.PROJECTPATH + "src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        config.setFileOutConfigList(focList);
        return config;
    }
}