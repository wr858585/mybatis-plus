package com.atguigu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

//只要idea安装了lombok插件，并引入了lombok依赖，就可以使用lombok提供的注解来简化javabean的开发
@Data
//@Getter
//@Setter
@ToString//重写ToString
@NoArgsConstructor//无参构造器：使用第三方工具时，如果设计对象的创建，一般通过反射根据全类名创建
//Class.forName("com.atguigu.entity.User").newInstance()，这个方法会去调javaBean的无参构造器，所以必须提供
@AllArgsConstructor//全参构造器
public class User {

    @TableId(type = IdType.ASSIGN_ID) //雪花算法，以后只使用此方式生成分表的主键
//    @TableId(type = IdType.ASSIGN_UUID)
//    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //新增时自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //新增和更新时，都需要自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)//不是FieldFill.UPDATE
    private Date gmtModified;

}
