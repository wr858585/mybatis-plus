package com.atguigu.mapper;

import com.atguigu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

//mybatis-plus提供了一个BaseMapper
//只要javaBean属性名和数据库表的字段名字一致（完全匹配映射）
//就可以通过指定BaseMapper的泛型为这个javaBean的类型，就会自动生成单表的所有CRUD操作

//没有写@Mapper注解，在主启动上写了@ScanMapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
