package com.example.major.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class TestPapers2Dao extends BaseDaoImpl<TestPapers2, String> {

	public static TestPapers2Dao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper
				.getHelper(context);
		final TestPapers2Dao dao = (TestPapers2Dao) helper.getDao(TestPapers2Dao.class, TestPapers2.class);
		return dao;
	}

	public TestPapers2Dao(ConnectionSource connectionSource, Class<TestPapers2> dataClass)
			throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public List<TestPapers2> readUnitsModel() {
		List<TestPapers2> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	} 
	
	public void save(final List<TestPapers2> simulationTests) {
		try {
			callBatchTasks(new Callable<Void>() {
				public Void call() throws SQLException {
					for (TestPapers2 item : simulationTests) {
						save(item);
					}
					return null;
				}

			});
		} catch (Exception e) {
		}
	}

	private void save(TestPapers2 item) {
		if (item == null)
			return;
		try {
			createOrUpdate(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
