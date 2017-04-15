package com.example.major;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.major.help.Units;
import com.example.major.help.UnitsDao;

public class ExerciseActivity extends Activity implements OnClickListener {
	private ExpandableListView expandableListView;
	private ImageView arrowImageView;
	private List<Units> readUnitsModel;
	private ArrayList<String> charpterNames;
	private HashMap<String, ArrayList<String>> maps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cowork_comment);
		getData();
		initView();
		initData();
	}

	private void getData() {
		readUnitsModel = UnitsDao.newInstance(this).readUnitsModel();
		charpterNames = new ArrayList<String>();
		maps = new HashMap<String, ArrayList<String>>();
		if (readUnitsModel != null && !readUnitsModel.isEmpty()) {
			for (Units unit : readUnitsModel) {
				if (!charpterNames.contains(unit.getChapterName())) {
					ArrayList<String> ids = new ArrayList<String>();
					ids.add(unit.getUnitsName());
					maps.put(unit.getChapterName(), ids);
					charpterNames.add(unit.getChapterName());
				} else {
					maps.get(unit.getChapterName()).add(unit.getUnitsName());
				}
			}
		}
	}

	private void initData() {
		arrowImageView.setOnClickListener(this);
		initExpandableListView();
	}

	private void initExpandableListView() {
		expandableListView.setGroupIndicator(null);
		expandableListView.setCacheColorHint(0);
		expandableListView.setItemsCanFocus(true);
		CoworkCommentAdapter adapter = new CoworkCommentAdapter();
		expandableListView.setAdapter(adapter);
	}

	private void initView() {
		expandableListView = (ExpandableListView) findViewById(R.id.eListView);
		arrowImageView = (ImageView) findViewById(R.id.arrow);
	}

	class CoworkCommentAdapter extends BaseExpandableListAdapter {

		@Override
		public String getChild(int groupPosition, int childPosition) {
			return maps.get(charpterNames.get(groupPosition))
					.get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View view, ViewGroup parent) {
			ChildViewHolder holder = null;
			if (view == null) {
				LayoutInflater inflater = LayoutInflater
						.from(ExerciseActivity.this);
				view = inflater
						.inflate(R.layout.cowork_comment_childview, null);
				holder = new ChildViewHolder();
				view.setTag(holder);
				holder.textName = (TextView) view
						.findViewById(R.id.title_content);
			} else
				holder = (ChildViewHolder) view.getTag();
			final String childString = getChild(groupPosition, childPosition);
			holder.textName.setText(childString);// ����
			final int count = getchildTotalCount(groupPosition) + childPosition;
			holder.textName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// ��ת����
					Intent intent = new Intent(ExerciseActivity.this,
							AnwserQuestionActivity.class);
					intent.putExtra(
							AnwserQuestionActivity.class.getSimpleName(), count);
					intent.putExtra("name", childString);
					startActivity(intent);
				}
			});
			return view;
		}

		public int getchildTotalCount(int groupPosition) {
			int i = 0;
			int count = 0;
			while (groupPosition > i) {
				count += getChildrenCount(i);
				i++;
			}
			return count;

		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return maps.get(charpterNames.get(groupPosition)).size();
		}

		@Override
		public String getGroup(int groupPosition) {
			return charpterNames.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return charpterNames.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded,
				View view, ViewGroup parent) {
			GroupViewHolder holder = null;
			if (view == null) {
				LayoutInflater inflater = LayoutInflater
						.from(ExerciseActivity.this);
				view = inflater
						.inflate(R.layout.cowork_comment_groupview, null);
				holder = new GroupViewHolder();
				view.setTag(holder);
				holder.imgPoint = (ImageView) view.findViewById(R.id.img_point);
				holder.text_name = (TextView) view
						.findViewById(R.id.text_groupName);
			} else
				holder = (GroupViewHolder) view.getTag();

			initGroupHolderView(groupPosition, isExpanded, holder);
			return view;
		}

		private void initGroupHolderView(final int groupPosition,
				boolean isExpanded, GroupViewHolder holder) {
			holder.text_name.setText(getGroup(groupPosition));// ����
			if (isExpanded)
				holder.imgPoint.setImageResource(R.drawable.arrow_up);
			else
				holder.imgPoint.setImageResource(R.drawable.arrow_down);
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}

	class GroupViewHolder // ��view
	{
		TextView text_name;
		ImageView imgPoint;
	}

	class ChildViewHolder // ��view
	{
		TextView textName;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.arrow:
			finish();
			break;

		default:
			break;
		}
	}
}
