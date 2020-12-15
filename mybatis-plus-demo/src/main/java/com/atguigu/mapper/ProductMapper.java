package com.atguigu.mapper;

import com.atguigu.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
//BaseMapper<T>的泛型就是告诉mp，我们要根据里面的javaBean生成
public interface ProductMapper extends BaseMapper<Product> {
}
