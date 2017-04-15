package com.example.major;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.example.major.help.AssetsDBCopy;
import com.example.major.help.Exams;
import com.example.major.help.ExamsDao;
import com.example.major.help.LoadingProgressDialog;
import com.example.major.help.SimulatedTests;
import com.example.major.help.SimulationTests;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView firstImageView;
	private ImageView secondImageView;
	private ImageView thirdImageView;
	private ImageView forthImageView;
	private ImageView fiveImageView;
	private ImageView sixImageView;
	private Handler subHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AssetsDBCopy.DB_COPY_MSG_FAIL:
				initDatabase();
				break;
			case AssetsDBCopy.DB_COPY_MSG_OK:
				if (dialog != null && dialog.isShowing()){
					dialog.dismiss();
				}
				break;

			default:
				break;
			}
		};
	};
	private LoadingProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}


	private void init() {
		initDatabase();
		initView();
		initEvent();
	}

	private void initDatabase() {
		if (dialog != null && dialog.isShowing())
			dialog = LoadingProgressDialog.show(this, "???", "???????????....");
		AssetsDBCopy.dbCopy(this, subHandler);
	}

	private void initEvent() {
		firstImageView.setOnClickListener(this);
		secondImageView.setOnClickListener(this);
		thirdImageView.setOnClickListener(this);
		forthImageView.setOnClickListener(this);
		fiveImageView.setOnClickListener(this);
		sixImageView.setOnClickListener(this);
	}

	private void initView() {
		firstImageView = (ImageView) findViewById(R.id.first);
		secondImageView = (ImageView) findViewById(R.id.second);
		thirdImageView = (ImageView) findViewById(R.id.third);
		forthImageView = (ImageView) findViewById(R.id.forth);
		fiveImageView = (ImageView) findViewById(R.id.five);
		sixImageView = (ImageView) findViewById(R.id.six);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.first:
			startActivity(new Intent(this, ExerciseActivity.class));
			break;
		case R.id.second:
			Intent intent = new Intent(this, TestActivity.class);
			intent.putExtra(TestActivity.class.getSimpleName(), SimulatedTests.class.getSimpleName());
			startActivity(intent);
			break;
		case R.id.third:
			
			break;
		case R.id.forth:
			Intent intent1 = new Intent(this, TestActivity.class);
			intent1.putExtra(TestActivity.class.getSimpleName(), SimulationTests.class.getSimpleName());
			startActivity(intent1);
			break;
		case R.id.five:

			break;
		case R.id.six:

			break;

		default:
			break;
		}
	}

}
