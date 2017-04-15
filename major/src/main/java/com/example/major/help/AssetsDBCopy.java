package com.example.major.help;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;

public class AssetsDBCopy {

	public final static int DB_COPY_MSG_FAIL = 1801;
	public final static int DB_COPY_MSG_OK = 1802;

	public final static String DATABASE_NAME = "data.db";

	public final static void dbCopy(Context context, Handler handler) {

		AssetsDBCopy action = new AssetsDBCopy();
		action.copy(context, handler);
	}

	private void copy(final Context context, final Handler handler) {
		new Thread() {
			public void run() {
				boolean copyfileResult = copyFile(context);
				if (!copyfileResult) {
					if (handler != null)
						handler.sendEmptyMessage(DB_COPY_MSG_FAIL);
					return;
				}
				handler.sendEmptyMessage(DB_COPY_MSG_OK);
			}
		}.start();
	}

	private boolean copyFile(Context context) {
		File desFile = context.getDatabasePath(DATABASE_NAME);
		InputStream istream = null;
		OutputStream ostream = null;
		try {
			if (!desFile.exists()) {
				File parent = desFile.getParentFile();
				if (!parent.exists())
					parent.mkdirs();
				desFile.createNewFile();
			}

			AssetManager am = context.getAssets();
			istream = am.open(DATABASE_NAME);
			ostream = new FileOutputStream(desFile);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = istream.read(buffer)) > 0) {
				ostream.write(buffer, 0, length);
			}
			istream.close();
			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (istream != null)
					istream.close();
				if (ostream != null)
					ostream.close();
				istream = null;
				ostream = null;
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			return false;
		}
		return true;
	}

}
