package com.example.major.help;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

public class MajorDao extends BaseDaoImpl<Major, String> {

	public static MajorDao newInstance(Context context) {
		final OrmDataBaseHelper helper = (OrmDataBaseHelper) OrmDataBaseHelper
				.getHelper(context);
		final MajorDao dao = (MajorDao) helper.getDao(MajorDao.class, Major.class);
		return dao;
	}

	public MajorDao(ConnectionSource connectionSource, Class<Major> dataClass)
			throws SQLException {
		super(connectionSource, dataClass);
	}

}
