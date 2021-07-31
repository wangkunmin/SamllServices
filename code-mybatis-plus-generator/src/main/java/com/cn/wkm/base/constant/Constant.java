package com.cn.wkm.base.constant;


import com.cn.wkm.utils.BaseCodeGenerator;

/**
 * @ClassName Constant
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 17:39
 * @ModifyDate 2021/7/31 17:39
 */
public class Constant {
    /**
     * 参数配置
     * @param args
     */
    //作者
    public static final String AUTHOR = "wkm";
    //表名前缀
    //private static final String[] TABLEPREFIX = {"t_"};
    public static final String[] TABLEPREFIX = {"small_config."};
    //表名（使用小写）
    public static final String[] TABLENAMES = {"sys_database","sys_user_database"};
    //包名称
    public static final String PACKAGENAME = "com.cn.wkm";
    //用户名
    public static final String USERNAME = "small";
    //密码
    public static final String PASSWORD = "123456";
    //数据库url
    public static final String URL = "jdbc:mysql://192.168.27.152:3306/small_config?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    public static final String DRIVERNAME = "com.mysql.jdbc.Driver";

    public static String PROJECTPATH = "";
    static {
        // 当前项目绝对路径
        PROJECTPATH = BaseCodeGenerator.class.getClassLoader().getResource("").getPath().replace("target/classes/","");
    }
}