package com.wuxin.blog.config.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件 MybatisPlusInterceptor
     * @return interceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 注册填充策略
     * @return  GlobalConfig
     */
    @Bean
    public GlobalConfig globalconfig() {
        GlobalConfig globalconfig = new GlobalConfig();
        globalconfig.setMetaObjectHandler(new MetaHandler());
        return globalconfig;
    }

    /**
     * mapper包扫描
     * @return  mapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.wuxin.blog.mapper");
        return mapperScannerConfigurer;
    }

    // @Bean
    // public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
    //     return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new CustomIdGenerator());
    // }

    // @Bean
    // public IdentifierGenerator idGenerator(CustomIdGenerator customIdGenerator) {
    //     customIdGenerator.nextId(Comment.class);
    //     return customIdGenerator;
    // }






}
