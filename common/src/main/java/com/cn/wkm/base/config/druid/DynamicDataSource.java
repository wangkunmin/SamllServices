package com.cn.wkm.base.config.druid;

import com.cn.wkm.utils.SpringContextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DynamicDataSource
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/30 16:51
 * @ModifyDate 2021/7/30 16:51
 */
@Log4j2
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 数据源MAP
     */
    private static final Map<Object, Object> DATA_SOURCES_MAP = new ConcurrentHashMap<>(64);
    /**
     * 当前数据源
     */
    private static final ThreadLocal<String> DATA_SOURCE_KEY = ThreadLocal.withInitial(() -> DruidConfiguration.DEFAULT_DATA_SOURCE);

    static {
        // 默认数据源
        DATA_SOURCES_MAP.put(DruidConfiguration.DEFAULT_DATA_SOURCE, SpringContextUtils.getBean(DruidConfiguration.DEFAULT_DATA_SOURCE));
    }

    public static Map<Object, Object> getDataSourcesMap() {
        return DATA_SOURCES_MAP;
    }

    public static Object getDataSource(String dbCode) {
        return DATA_SOURCES_MAP.get(dbCode);
    }

    public static Object putDataSource(String dbCode, Object dataSource) {
        return DATA_SOURCES_MAP.put(dbCode, dataSource);
    }

    /**
     * 获取当前数据源KEY
     *
     * @return 当前数据源KEY
     */
    public static String getDataSourceKey() {
        return DATA_SOURCE_KEY.get();
    }

    /**
     * 清空缓存
     */
    public static void clearDataSourcesMap() {
        DATA_SOURCES_MAP.clear();
    }

    /**
     * 清空当前线程变量
     */
    public static void clear() {
        DATA_SOURCE_KEY.remove();
    }

    /**
     * 设置当前数据源
     *
     * @param dbCode 数据源编码
     */
    public static void setDataSource(String dbCode) {
        // 设置前清空
        clear();
        DATA_SOURCE_KEY.set(dbCode);
        log.info("========================== 当前数据源 {}", dbCode);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DATA_SOURCE_KEY.get();
    }

    @Override
    @NonNull
    public DataSource determineTargetDataSource() {
        Assert.notNull(DATA_SOURCES_MAP, "DataSource router not initialized");
        Object lookupKey = getDataSourceKey();
        DataSource dataSource = (DataSource) DATA_SOURCES_MAP.get(lookupKey);
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
    }
}