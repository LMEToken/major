package com.example.major.help;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.major.R;

/********************************************************************
 
 *******************************************************************/

public class LoadingProgressDialog extends Dialog {

	private static final String TAG = LoadingProgressDialog.class.getSimpleName();
	private Context mContext = null;
	private String mLoadingTip = null;

	public LoadingProgressDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public LoadingProgressDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	public LoadingProgressDialog(Context context, int theme, CharSequence loadingTip) {
		super(context, theme);
		this.mContext = context;
		if (loadingTip != null) {
			this.mLoadingTip = String.valueOf(loadingTip);
		}
	}

	public static LoadingProgressDialog show(Context context) {
		return show(context, null);
	}

	public static LoadingProgressDialog show(Context context, CharSequence message) {
		return show(context, null, message);
	}

	public static LoadingProgressDialog show(Context context, CharSequence title, CharSequence message) {
		return show(context, title, message, false);
	}

	public static LoadingProgressDialog show(Context context, CharSequence title, CharSequence message,
			boolean cancelable) {
		return show(context, title, message, cancelable, null);
	}

	public static LoadingProgressDialog show(final Context context, CharSequence title, CharSequence message,
			boolean cancelable, OnCancelListener cancelListener) {
		
		if(context==null) return null;
		
		LoadingProgressDialog dialog = new LoadingProgressDialog(context, R.style.LoadingDialog, message);
		if (title != null) {
			dialog.setTitle(title);
		}
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		// ���������Service
		if (context instanceof Service) {
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		dialog.show();
		return dialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// WindowManager.LayoutParams params = getWindow().getAttributes();
		// params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		// this.getWindow().setAttributes(params);

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.loadingprogressdialog, null);
		TextView tvLoadingTip = (TextView) view.findViewById(R.id.tvLoadingTip);
		if (tvLoadingTip != null && mLoadingTip != null) {
			tvLoadingTip.setVisibility(View.VISIBLE);
			tvLoadingTip.setText(mLoadingTip);
		} else {
			tvLoadingTip.setVisibility(View.INVISIBLE);
		}
		setContentView(view);
		super.onCreate(savedInstanceState);
	}
	
	public void setMessage(String msg){
		mLoadingTip = msg;
		TextView tvLoadingTip = (TextView) this.findViewById(R.id.tvLoadingTip);
		if (tvLoadingTip != null && mLoadingTip != null) 
			tvLoadingTip.setText(mLoadingTip);
	}

}