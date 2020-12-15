package com.atguigu;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//自动配置：根据当前的依赖的环境，判断创建哪些配置类的对象；以及根据配置对象类，去初始化需要使用的模板对象
@SpringBootApplication
//注意：mapperscan只能扫描mapper接口创建实现类对象，所以basePackages必须写到mapper目录
//不能写com，不能写com.atguigu；必须写到com.atguigu.mapper，才能扫到mappers
@MapperScan(basePackages = {"com.atguigu.mapper"})
public class MybatisPlusDemoApplication {

	/**
	 * 数据库表：entity --> 逆向工程生成
	 * 页面中显示的数据：vo（view object）
	 * 两个微服务之间传递的数据：dto（data transfer object）
	 * 请求提交的数据：formObject
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusDemoApplication.class, args);
	}

	//OptimisticLockerInterceptor：mybatis的拦截器，可以再sql交给数据库之前对sql进行修改
	//生成要更新的版本号以及更新条件
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor(){
		return new OptimisticLockerInterceptor();
	}

	//mp分页拦截器
	//以前这些bean放在mp的配置文件里，现在一般不写配置文件所以直接写进主启动类里面就可以了
	@Bean
	public PaginationInterceptor paginationInterceptor(){
		return new PaginationInterceptor();
	}

}
