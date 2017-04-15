package com.example.major.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class TestPapersDao extends BaseDaoImpl<TestPapers, String> {

	public static TestPapersDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper.getHelper(context);
		final TestPapersDao dao = (TestPapersDao) helper.getDao(TestPapersDao.class, TestPapers.class);
		return dao;
	}

	public TestPapersDao(ConnectionSource connectionSource, Class<TestPapers> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public List<TestPapers> readUnitsModel() {
		List<TestPapers> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	}

	public void save(final List<TestPapers> simulatedTests) {
		try {
			callBatchTasks(new Callable<Void>() {
				public Void call() throws SQLException {
					for (TestPapers item : simulatedTests) {
						save(item);
					}
					return null;
				}

			});
		} catch (Exception e) {
		}
	}

	private void save(TestPapers item) {
		if (item == null)
			return;
		try {
			createOrUpdate(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
