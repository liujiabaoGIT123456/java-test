package com.testall.demo.other;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class AutoEntity {
    public static void main(String[] args){
        //构建一个代码自动给生成器对象
        AutoGenerator mpg = new AutoGenerator();
        //配置策略
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\file");
        gc.setAuthor("liujiabao");	//设置作者姓名
        gc.setOpen(false);
        gc.setFileOverride(false);  //是否覆盖
        gc.setServiceName("%sService");     //去除service的I前缀（如果不设置service前面将会多一个I）
        mpg.setGlobalConfig(gc);

        //2、配置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("ABC");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        //3、配置包
        PackageConfig pc = new PackageConfig();
        //pc.setParent("\\file");	 //设置生成在哪个父包下
        pc.setEntity("pojo");	//设置实体类包名
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("TEST");    //设置要映射的表(可包含多个)
        //strategy.setExclude("TEST");	//也可设置不需要映射的表
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        //strategy.setLogicDeleteFieldName("deleted");   //设置逻辑删除字段
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        //乐观锁
//        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        //执行
        mpg.execute();
    }
}

