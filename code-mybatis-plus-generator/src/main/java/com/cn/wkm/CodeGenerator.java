package com.cn.wkm;

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
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

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
public class CodeGenerator {
    /**
     * 参数配置
     * @param args
     */
    //作者
    private static final String AUTHOR = "wkm";
    //表名前缀
    //private static final String[] TABLEPREFIX = {"t_"};
    private static final String[] TABLEPREFIX = {"small_config."};
    //表名（使用小写）
    private static final String[] TABLENAMES = {"sys_database","sys_user_database"};
    //包名称
    private static final String PACKAGENAME = "com.cn.wkm";
    //用户名
    private static final String USERNAME = "small";
    //密码
    private static final String PASSWORD = "123456";
    //数据库url
    private static final String URL = "jdbc:mysql://192.168.27.152:3306/small_config?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    private static final String DRIVERNAME = "com.mysql.jdbc.Driver";

    private static String PROJECTPATH = "";
    static {
        // 当前项目绝对路径
        PROJECTPATH = CodeGenerator.class.getClassLoader().getResource("").getPath().replace("target/classes/","");
    }


    public static DataSourceConfig dataSourceConfig(){
        /**
         * 数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setDriverName(DRIVERNAME)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setUrl(URL);
        return dsc;
    }
    public static GlobalConfig globalConfig(){
        /**
         * 全局变量配置
         */
        GlobalConfig gc = new GlobalConfig();

        String outPutDir = PROJECTPATH + "src/main/java";
        // 输出路径
        gc.setOutputDir(outPutDir) //设置输出路径
                .setFileOverride(true) //第二次生成会把第一次生成的覆盖掉
                .setAuthor(AUTHOR)
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
    public static StrategyConfig dataTable(){
        /**
         * 数据库表配置
         */
        StrategyConfig strategy = new StrategyConfig();
        strategy.setSkipView(true) //是否跳过试图 默认false
                .setTablePrefix(TABLEPREFIX)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel) //数据库表字段映射到实体的命名策略
                .setCapitalMode(true)//设置全局大写命名
                .setInclude(TABLENAMES) //指定表
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
        strategy.setSuperEntityClass("com.cn.wkm.base.entity.BaseEntity");
        strategy.setSuperEntityColumns("id", "update_by", "update_at", "create_by", "create_at","is_delete");


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

    public static void main(String[] args)
    {
        //用来获取Mybatis-Plus.properties文件的配置信息
        AutoGenerator autoGenerator = new AutoGenerator();
        /**
         * 包配置
         */
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGENAME)
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setEntity("entity");

        autoGenerator.setGlobalConfig(globalConfig())
                .setDataSource(dataSourceConfig())
                .setStrategy(dataTable())
                .setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //使用模板引擎避免xml重复生成
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        autoGenerator.setTemplate(templateConfig);

        //指定模板引擎
        autoGenerator.setCfg(injectionConfig());
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    public static InjectionConfig  injectionConfig() {
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
        String templatePath = "/templates/controller.java.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = PROJECTPATH + "src/main/java/"+ PACKAGENAME.replace(".","/") + "/controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }
        });

        // 自定义service的代码模板
        templatePath = "/templates/service.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = PROJECTPATH + "src/main/java/"+ PACKAGENAME.replace(".","/") + "/service";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceName());
                return entityFile;
            }
        });

        // 自定义service的代码模板
        templatePath = "/templates/serviceImpl.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = PROJECTPATH + "src/main/java/"+ PACKAGENAME.replace(".","/") + "/service/impl";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceImplName());
                return entityFile;
            }
        });

        // 配置自定义输出模板
        templatePath = "/templates/mapper.xml.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                return PROJECTPATH + "src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        config.setFileOutConfigList(focList);
        return config;
    }
}