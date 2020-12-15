package com.atguigu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component//一定要放入容器
public class MyMetaObjectHandler implements MetaObjectHandler {

    //新增时的自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        //给新增的对象自动填充gmtCreate,gmtModified
        metaObject.setValue("gmtCreate", new Date());//参数1代表要设置的属性名（注意：该属性一定要有set方法才行）
        metaObject.setValue("gmtModified", new Date());
    }

    //更新时的自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        //给更新的对象自动填充gmtModified
        metaObject.setValue("gmtModified", new Date());
    }
}
