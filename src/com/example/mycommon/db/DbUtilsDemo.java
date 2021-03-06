package com.example.mycommon.db;

/**
 * 对数据库操作的基本实例
 * 
 * @author Kaka
 * 
 */
public class DbUtilsDemo {
//	DbUtils db = DbUtils.create(this);
//	User user = new User(); //这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性
//	user.setEmail("wyouflf@qq.com");
//	user.setName("wyouflf");
//	db.save(user); // 使用saveBindingId保存实体时会为实体的id赋值
//
//	...
//	// 查找
//	Parent entity = db.findById(Parent.class, parent.getId());
//	List<Parent> list = db.findAll(Parent.class);//通过类型查找
//
//	Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","=","test"));
//
//	// IS NULL
//	Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","=", null));
//	// IS NOT NULL
//	Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","!=", null));
//
//	// WHERE id<54 AND (age>20 OR age<30) ORDER BY id LIMIT pageSize OFFSET pageOffset
//	List<Parent> list = db.findAll(Selector.from(Parent.class)
//	                                   .where("id" ,"<", 54)
//	                                   .and(WhereBuilder.b("age", ">", 20).or("age", " < ", 30))
//	                                   .orderBy("id")
//	                                   .limit(pageSize)
//	                                   .offset(pageSize * pageIndex));
//
//	// op为"in"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
//	Parent test = db.findFirst(Selector.from(Parent.class).where("id", "in", new int[]{1, 2, 3}));
//	// op为"between"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
//	Parent test = db.findFirst(Selector.from(Parent.class).where("id", "between", new String[]{"1", "5"}));
//
//	DbModel dbModel = db.findDbModelAll(Selector.from(Parent.class).select("name"));//select("name")只取出name列
//	List<DbModel> dbModels = db.findDbModelAll(Selector.from(Parent.class).groupBy("name").select("name", "count(name)"));
//	...
//
//	List<DbModel> dbModels = db.findDbModelAll(sql); // 自定义sql查询
//	db.execNonQuery(sql) // 执行自定义sql
//	...

}
