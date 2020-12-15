package com.atguigu;

import com.atguigu.entity.Product;
import com.atguigu.entity.User;
import com.atguigu.mapper.ProductMapper;
import com.atguigu.mapper.UserMapper;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	//这里，idea会报错，因为userMapper没有在mapper下的UserMapper接口上加到IOC容器中
	//solution：在UserMapper接口上写@Repository
	private UserMapper userMapper;

	//1. 查询所有
	@Test
	void selectAll() {
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

	//2. 指定条件的查询
	@Test
	public void testSelectByConditionAnd(){
		//查询条件：查询id>5的并且姓名包含 辉 的用户
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.gt("id",5);//参数1：数据库字段名称
		queryWrapper.likeLeft("name","辉");
		List<User> users = userMapper.selectList(queryWrapper);
		System.out.println("users = " + users);
	}

	@Test
	public void testSelectByConditionOr(){
		//查询条件：查询id>5的并且姓名包含 辉 的用户
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.gt("id",5).or().likeRight("name","辉");
		List<User> users = userMapper.selectList(queryWrapper);
		System.out.println("users = " + users);
	}

	//用selectByMap比较少，因为查询的条件提供的不多
	@Test
	public void testSelectByConditionByMap() {
		//查询条件：查询id>5的并且姓名包含 辉 的用户
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", 5);
		System.out.println(userMapper.selectByMap(map));
	}

	//3. 分页查询

	//4. 带条件的分页查询
	@Test
	public void queryPageByCondition(){
		//分页查询也需要使用拦截器修改sql，因为要添加：
		//查询分页需要提供两个基本参数：页码，每页显示的记录条数
		Page<User> userPage = new Page<>(1,3);
		//查询条件：name包含辉的数据且按照年龄降序排列
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name","辉");
		queryWrapper.orderByDesc("age", "id");//先按年龄降序排列，如果age相等，按照id降序排列
		userPage = userMapper.selectPage(userPage, queryWrapper);
		System.out.println("userPage = " + userPage);
	}

	//5. 查询指定列的数据集合
	@Test
	public void testQueryColumns(){
		//查询指定列的需求以后会碰到，可以提高查询的性能（从内存中查询）
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("name","email","age");//指定要查询的列
		queryWrapper.gt("age",5);
		List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
//		maps.forEach(System.out::println);
		for (Map<String, Object> map : maps) {
			//遍历的每个map表示一行记录的映射：  map本质和javabean一样都表示一个关系的映射
			System.out.println("map = " + map);
		}

	}

	//6. 新增
	@Test
	public void insert(){
		User user = new User();
		//user.setId()，数据库未设置自增
		user.setName("渣渣辉");
		user.setAge(18);
		user.setEmail("zzh@atguigu.com");
		int i = userMapper.insert(user);
		System.out.println(i>0?"新增成功":"新增失败");
	}

	//7. 更新
	@Test
	public void update(){
		User user = userMapper.selectById(3);
		user.setName("jerry");
		userMapper.updateById(user);
	}

	//乐观锁
	@Test
	public void testOptimisticLock(){
		//解决方案：共享数据被更新时，如果把多个请求获取的数据版本一致化，只让一个可以去修改
		//A和B同时去读取数据
		Product productA = productMapper.selectById(1);
		Product productB = productMapper.selectById(1);
		//A先更新，涨价50
		productA.setPrice(productA.getPrice() + 50);
		productMapper.updateById(productA);
		//B再更新，减价30
		productB.setPrice(productB.getPrice() - 30);
		int i = productMapper.updateById(productB);
		if(i == 0){
			//更新失败
			//查询数据，获取最新version的数据
			productB = productMapper.selectById(1);
			productB.setPrice(productB.getPrice() - 30);
			productMapper.updateById(productB);
		}
		//显示更新后的价格
		System.out.println("更新完毕后的价格：" + productMapper.selectById(1).getPrice());
	}

	//8. 删除

}
