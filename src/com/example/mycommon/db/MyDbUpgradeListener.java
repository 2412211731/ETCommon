package com.example.mycommon.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

/**
 * 用于DbUtils修改表结构用 使用方法： DbUtils db1 =
 * DbUtils.create(context,Config.COURSE_DB_NAME, Config.COURSE_DB_VERSION, new
 * MyDbUpgradeListener()); 当版本号比当前版本大，则会执行本监听器的onUpgrade方法
 * 
 * @author kaka
 */
public class MyDbUpgradeListener implements DbUpgradeListener {
	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
		String sql = "ALTER TABLE course ADD time varchar(50)";
		if (oldVersion == 1) {
			updatTable(db, sql);
		} else {
			try {
				db.dropDb();
			} catch (DbException e) {
				LogUtils.e(e.getMessage(), e);
			}
		}
	}

	public void updatTable(DbUtils db, String sql) {
		try {
			db.execNonQuery(sql);
			System.out.println(sql + ": success");
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
