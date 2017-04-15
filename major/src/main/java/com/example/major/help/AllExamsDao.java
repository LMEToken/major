package com.example.major.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

public class AllExamsDao extends BaseDaoImpl<AllExams, String> {

	public static AllExamsDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper.getHelper(context);
		final AllExamsDao dao = (AllExamsDao) helper.getDao(AllExamsDao.class, AllExams.class);
		return dao;
	}

	public AllExamsDao(ConnectionSource connectionSource, Class<AllExams> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public List<AllExams> readUnitsModel() {
		List<AllExams> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	}
	
	public AllExams readAllExamsByQuestion(String question){
		try {
			QueryBuilder<AllExams, String> queryBuilder = queryBuilder();
			Where<AllExams, String> eq = queryBuilder.where().eq("Questions", question);
			return eq.queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public void save(final ArrayList<AllExams> exam) {
		try {
			callBatchTasks(new Callable<Void>() {
				public Void call() throws SQLException {
					for (AllExams item : exam) {
						save(item);
					}
					return null;
				}

			});
		} catch (Exception e) {
		}
	}

	private void save(AllExams item) {
		if (item == null)
			return;
		try {
			createOrUpdate(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
