package com.example.major;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.major.help.SimulatedTests;
import com.example.major.help.SimulatedTestsDao;
import com.example.major.help.SimulationTests;
import com.example.major.help.SimulationTestsDao;

import java.util.List;

public class TestActivity extends Activity implements OnClickListener {
	private ListView listView;
	private ImageView arrowImageView;
	private Button tabButton;
	private Button tab2Button;
	private String string;
	private List<SimulatedTests> simulatedTests;
	private List<SimulationTests> simulationTests;
	private TextView titleView;
	private int index = 1;
	private int type =0;
	private CoworkCommentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_test);
		getData();
		initView();
		initData();
	}

	private void getData() {
		Intent intent = getIntent();
		string = intent.getStringExtra(TestActivity.class.getSimpleName());
		if (SimulatedTests.class.getSimpleName().equals(string)) {
			type=1;
			simulatedTests = SimulatedTestsDao.newInstance(this).readUnitsModel();
		} else if (SimulationTests.class.getSimpleName().equals(string)) {
			type=2;
			simulationTests = SimulationTestsDao.newInstance(this).readUnitsModel();
		}
	}

	private void initData() {
		if (SimulatedTests.class.getSimpleName().equals(string)) {
			SimulatedTests simulatedTests1 = simulatedTests.get(0);
			SimulatedTests simulatedTests2 = simulatedTests.get(1);
			String exam_name = simulatedTests1.getExam_name();
			String substring = exam_name.substring(0, exam_name.length() - 1);
			titleView.setText(substring);
			tabButton.setText(simulatedTests1.getSubject_name());
			tab2Button.setText(simulatedTests2.getSubject_name());
		} else if (SimulationTests.class.getSimpleName().equals(string)) {
			SimulatedTests simulatedTests1 = simulationTests.get(0);
			SimulatedTests simulatedTests2 = simulationTests.get(1);
			String exam_name = simulatedTests1.getExam_name();
			String substring = exam_name.substring(0, exam_name.length() - 1);
			titleView.setText(substring);
			tabButton.setText(simulatedTests1.getSubject_name());
			tab2Button.setText(simulatedTests2.getSubject_name());
		}
		arrowImageView.setOnClickListener(this);
		tabButton.setOnClickListener(this);
		tab2Button.setOnClickListener(this);
		tabButton.setBackgroundResource(R.drawable.rounded_left_bg_blue);
		tabButton.setTextColor(getResources().getColor(R.color.white));
		tab2Button.setBackgroundResource(R.drawable.rounded_right_bg_white);
		tab2Button.setTextColor(getResources().getColor(R.color.this_blue));
		initExpandableListView();
	}

	private void initExpandableListView() {
		listView.setCacheColorHint(0);
		listView.setItemsCanFocus(true);
		adapter = new CoworkCommentAdapter();
		listView.setAdapter(adapter);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		arrowImageView = (ImageView) findViewById(R.id.arrow);
		titleView = (TextView) findViewById(R.id.title);
		tab2Button = (Button) findViewById(R.id.tab_2);
		tabButton = (Button) findViewById(R.id.tab_1);
	}

	class CoworkCommentAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (SimulatedTests.class.getSimpleName().equals(string)) {
				return simulatedTests != null ? simulatedTests.size() / 2 : 0;
			} else if (SimulationTests.class.getSimpleName().equals(string)) {
				return simulationTests != null ? simulationTests.size() / 2 : 0;
			}
			return 0;
		}

		@Override
		public SimulatedTests getItem(int position) {
			if (SimulatedTests.class.getSimpleName().equals(string)) {
				switch (index) {
				case 1:
					return simulatedTests != null ? simulatedTests.get(position * 2) : null;
				case 2:
					return simulatedTests != null ? simulatedTests.get(position * 2 + 1) : null;

				default:
					break;
				}
			} else if (SimulationTests.class.getSimpleName().equals(string)) {
				switch (index) {
				case 1:
					return simulationTests != null ? simulationTests.get(position * 2) : null;
				case 2:
					return simulationTests != null ? simulationTests.get(position *2 +1) : null;

				default:
					break;
				}
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			switch (index) {
			case 1:
				return position * 2;
			case 2:
				return position * +1;

			default:
				break;
			}
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			ChildViewHolder holder = null;
			if (view == null) {
				LayoutInflater inflater = LayoutInflater.from(TestActivity.this);
				view = inflater.inflate(R.layout.test_item, null);
				holder = new ChildViewHolder();
				view.setTag(holder);
				holder.AB = (TextView) view.findViewById(R.id.AB);
				holder.ABC = (TextView) view.findViewById(R.id.ABC);
				holder.ABCD = (TextView) view.findViewById(R.id.ABCD);
				holder.linearlayout = (LinearLayout) view.findViewById(R.id.linearlayout);
			} else
				holder = (ChildViewHolder) view.getTag();
			final SimulatedTests item = getItem(position);
			holder.AB.setText(item.getExam_name());
			holder.ABCD.setText(item.getSubject_name());
			holder.ABC.setText("成绩：0");
			holder.linearlayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(TestActivity.this, TestAnwserQuestionActivity.class);
					intent.putExtra("name", item.getExam_name());
					intent.putExtra("type", type);
					intent.putExtra("index", getItemId(position));
					startActivity(intent);
				}
			});
			return view;
		}

	}

	class ChildViewHolder // 子view
	{
		TextView AB;
		TextView ABC;
		TextView ABCD;
		ImageView A;
		LinearLayout linearlayout;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.arrow:
			finish();
			break;
		case R.id.tab_1:
			// 换下资源 然后notify
			if (index == 1)
				return;
			tabButton.setBackgroundResource(R.drawable.rounded_left_bg_blue);
			tabButton.setTextColor(getResources().getColor(R.color.white));
			tab2Button.setBackgroundResource(R.drawable.rounded_right_bg_white);
			tab2Button.setTextColor(getResources().getColor(R.color.this_blue));
			index = 1;
			adapter.notifyDataSetChanged();
			break;
		case R.id.tab_2:
			// 换下资源 然后notify
			if (index == 2)
				return;
			tab2Button.setBackgroundResource(R.drawable.rounded_right_bg_blue);
			tab2Button.setTextColor(getResources().getColor(R.color.white));
			tabButton.setBackgroundResource(R.drawable.rounded_left_bg_white);
			tabButton.setTextColor(getResources().getColor(R.color.this_blue));
			index = 2;
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

}
