package com.example.major.help;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class SimulationTestsDao extends BaseDaoImpl<SimulationTests, String> {

	public static SimulationTestsDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper
				.getHelper(context);
		final SimulationTestsDao dao = (SimulationTestsDao) helper.getDao(SimulationTestsDao.class, SimulationTests.class);
		return dao;
	}

	public SimulationTestsDao(ConnectionSource connectionSource, Class<SimulationTests> dataClass)
			throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public List<SimulationTests> readUnitsModel() {
		List<SimulationTests> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	} 

}
