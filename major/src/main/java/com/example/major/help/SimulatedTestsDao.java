package com.example.major.help;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class SimulatedTestsDao extends BaseDaoImpl<SimulatedTests, String> {

	public static SimulatedTestsDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper
				.getHelper(context);
		final SimulatedTestsDao dao = (SimulatedTestsDao) helper.getDao(SimulatedTestsDao.class, SimulatedTests.class);
		return dao;
	}

	public SimulatedTestsDao(ConnectionSource connectionSource, Class<SimulatedTests> dataClass)
			throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public List<SimulatedTests> readUnitsModel() {
		List<SimulatedTests> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	} 

}
