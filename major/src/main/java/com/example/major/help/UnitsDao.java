package com.example.major.help;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class UnitsDao extends BaseDaoImpl<Units, String> {

	public static UnitsDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper
				.getHelper(context);
		final UnitsDao dao = (UnitsDao) helper.getDao(UnitsDao.class, Units.class);
		return dao;
	}

	public UnitsDao(ConnectionSource connectionSource, Class<Units> dataClass)
			throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public List<Units> readUnitsModel() {
		List<Units> list = null;
		try {
			list = this.queryForAll();
		} catch (Exception e) {
		}
		return list;
	} 

}
