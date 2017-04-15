package com.example.major.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ExamsDao extends BaseDaoImpl<Exams, String> {

	public static ExamsDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper.getHelper(context);
		final ExamsDao dao = (ExamsDao) helper.getDao(ExamsDao.class, Exams.class);
		return dao;
	}

	public ExamsDao(ConnectionSource connectionSource, Class<Exams> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public List<Exams> readUnitsModel() {
		List<Exams> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	}

	public void save(final ArrayList<Exams> exam) {
		try {
			callBatchTasks(new Callable<Void>() {
				public Void call() throws SQLException {
					for (Exams item : exam) {
						save(item);
					}
					return null;
				}

			});
		} catch (Exception e) {
		}
	}

	private void save(Exams item) {
		if (item == null)
			return;
		try {
			createOrUpdate(item);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
