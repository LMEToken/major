package com.example.major.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrmDataBaseHelper extends OrmLiteSqliteOpenHelper {

	public static final int DATABASE_VERSION = 6;

	private static HashMap<String, OrmLiteSqliteOpenHelper> dbHelpers = new HashMap<String, OrmLiteSqliteOpenHelper>();

	@SuppressWarnings("rawtypes")
	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private Map<String, BaseDaoImpl<?, ?>> implDaos = new HashMap<String, BaseDaoImpl<?, ?>>();

	private final static Class<?>[] TABLE_CLASSES = new Class[]{
			// ��ṹ
			AllExams.class

	};

	public OrmDataBaseHelper(Context context) {
		this(context, AssetsDBCopy.DATABASE_NAME);
	}

	public OrmDataBaseHelper(Context context, final String dbName) {
		super(context, dbName, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			for (Class<?> table : TABLE_CLASSES) {
				TableUtils.createTableIfNotExists(connectionSource, table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			for (Class<?> table : TABLE_CLASSES)
				TableUtils.dropTable(connectionSource, table, true);

			onCreate(database, connectionSource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized Dao getDao(Class clazz) {
		Dao dao = null;
		try {
			String className = clazz.getSimpleName();

			if (daos.containsKey(className)) {
				dao = daos.get(className);
			}
			if (dao == null) {
				dao = super.getDao(clazz);
				daos.put(className, dao);
			}
		} catch (Exception e) {
		}
		return dao;
	}

	public synchronized <T> BaseDaoImpl<T, ?> getDao(Class<? extends BaseDaoImpl<T, ?>> daoClass, Class<T> clazz) {

		BaseDaoImpl<T, ?> dao = null;
		try {
			String className = daoClass.getSimpleName();
			if (implDaos.containsKey(className)) {
				dao = (BaseDaoImpl<T, ?>) implDaos.get(className);
			} else {
				Object[] arguments = new Object[] { connectionSource, clazz };
				Constructor<?> daoConstructor = findConstructor(daoClass, arguments);
				if (daoConstructor == null) {
					arguments = new Object[] { connectionSource };
					daoConstructor = findConstructor(daoClass, arguments);
					if (daoConstructor == null) {
					}
				}
				try {
					if (daoConstructor != null)
						dao = (BaseDaoImpl<T, ?>) daoConstructor.newInstance(arguments);
					logger.debug("created dao for class {} from constructor", clazz);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (dao != null)
					implDaos.put(className, dao);
			}
		} catch (Exception e) {
		}
		return dao;
	}

	private static Constructor<?> findConstructor(Class<?> daoClass, Object[] params) {
		for (Constructor<?> constructor : daoClass.getConstructors()) {
			Class<?>[] paramsTypes = constructor.getParameterTypes();
			if (paramsTypes.length == params.length) {
				boolean match = true;
				for (int i = 0; i < paramsTypes.length; i++) {
					if (!paramsTypes[i].isAssignableFrom(params[i].getClass())) {
						match = false;
						break;
					}
				}
				if (match) {
					return constructor;
				}
			}
		}
		return null;
	}

	@Override
	public synchronized void close() {
		super.close();
		for (String key : daos.keySet()) {
			Dao dao = daos.get(key);
			dao = null;
		}
		for (String key : implDaos.keySet()) {
			Dao dao = implDaos.get(key);
			dao = null;
		}
	}

	public void rollbackTransaction(List<Dao> daos) {
		for (final Dao dao : daos) {
			try {
				dao.rollBack(this.getConnectionSource().getReadWriteConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static OrmLiteSqliteOpenHelper getHelper(Context context) {
		OrmLiteSqliteOpenHelper helper = dbHelpers.get(AssetsDBCopy.DATABASE_NAME);
		if (helper == null) {
			synchronized (OrmDataBaseHelper.class) {
				if (helper == null) {
					helper = new OrmDataBaseHelper(context);
					dbHelpers.put(AssetsDBCopy.DATABASE_NAME, helper);
				}
			}
		}
		return helper;
	}
}
