package com.example.major;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.major.help.AllExams;
import com.example.major.help.AllExamsDao;
import com.example.major.help.Exams;
import com.example.major.help.SimulatedTests;
import com.example.major.help.TestPapers;
import com.example.major.help.TestPapers2;
import com.example.major.help.TestPapers2Dao;
import com.example.major.help.TestPapersDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class TestAnwserQuestionActivity extends Activity implements OnClickListener {

	private TextView countTextView;
	private TextView doTextView;
	private LinearLayout checkboxContainer;
	private TextView titleContentTextView;
	private TextView titleTextView;
	private TextView anwserTextView;
	private TextView saveTextView;
	private TextView jiexiTextView;
	private AlertDialog dialog;
	private ImageView arrowImageView;
	private TextView titleTypeTextView;
	private LinearLayout answerTipLinearLayout;
	private TextView answerTextView;
	private TextView rightAnswerTipTextView;
	private TextView rightAnswerTextView;
	private ArrayList<String> answers1;

	private boolean flag;
	private List<TestPapers> simulatedTests;
	private List<TestPapers2> simulationTests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_anwser_question);
		getData();
		initView();
		initData();
	}

	private void getData() {
		Intent intent = getIntent();
		type = intent.getIntExtra("type", -1);
		string = intent.getStringExtra("name");
		int index = intent.getIntExtra("index", 0);
		if (type == 1) {
			simulatedTests = TestPapersDao.newInstance(this).readUnitsModel();
			subList = simulatedTests.subList(index * 120, index * 120 + 120);
		} else if (type == 2) {
			simulationTests = TestPapers2Dao.newInstance(this).readUnitsModel();
			subList2 = simulationTests.subList(index * 120, index * 120 + 120);
		}
	}

	int totalCount = 0;
	private AllExamsDao allExamsDao;
	private AllExams allExams;

	private void initData() {
		if (type == 1) {
			exams = subList.get(curPage);
			totalCount = subList.size();
		} else if (type == 2) {
			exams = subList2.get(curPage);
			totalCount = subList2.size();
		}
		if (allExamsDao == null)
			allExamsDao = AllExamsDao.newInstance(this);
		allExams = allExamsDao.readAllExamsByQuestion(exams.getQuestions());
		AllExams allExams2 = allExamsMap.get(exams.getQuestions());
		if (allExams2 != null) {
			savedAnswer = allExams2.getSavedAnswer();
		} else if (allExams != null && allExams.getSavedAnswer() != -1) {
			savedAnswer = allExams.getSavedAnswer();
		} else {
			savedAnswer = -1;
		}
		countTextView.setText(curPage + 1 + "/" + totalCount);
		doTextView.setText(savedAnswer == -1 ? "未做" : "已做");
		anwserTextView.setText(exams.getAnswer());
		rightAnswerTextView.setText(exams.getAnalysis());
		if (savedAnswer == -1) {
			rightAnswerTextView.setVisibility(View.GONE);
			rightAnswerTipTextView.setVisibility(View.GONE);
			anwserTextView.setVisibility(View.GONE);
			answerTipLinearLayout.setVisibility(View.GONE);
			flag = false;
		} else {
			rightAnswerTextView.setVisibility(View.VISIBLE);
			rightAnswerTipTextView.setVisibility(View.VISIBLE);
			anwserTextView.setVisibility(View.VISIBLE);
			answerTipLinearLayout.setVisibility(View.VISIBLE);
			flag = true;
		}

		titleContentTextView.setText(exams.getQuestions());
		titleTextView.setText(string);
		saveTextView.setOnClickListener(this);
		jiexiTextView.setOnClickListener(this);
		arrowImageView.setOnClickListener(this);
		initCheckBoxContainer();
	}

	private boolean firstDown = true;
	private float mX;
	private float mY;
	private int mIndex = 0;

	private static final int ACTION_FLIP_NONE = 0;
	private static final int ACTION_FLIP_TO_LEFT = 1;
	private static final int ACTION_FLIP_TO_RIGHT = 2;
	private int actionFlip = ACTION_FLIP_NONE;
	private int curPage = 0; // 当前被选中的view视图位置id
	private static int MIN_DELTA_FLIPPED = 80;
	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 当手指按下的时候
			x1 = (int) event.getX();
			y1 = (int) event.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			// 当手指离开的时候
			x2 = (int) event.getX();
			y2 = (int) event.getY();

			actionFlip = getAction();
			if (actionFlip == ACTION_FLIP_TO_LEFT || actionFlip == ACTION_FLIP_TO_RIGHT) {
				handleActionFlip(actionFlip);
				return true;
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	private void handleActionFlip(int actionFlip) {
		if (actionFlip == ACTION_FLIP_TO_RIGHT) {
			curPage--;
			if (curPage < 0) {
				curPage = totalCount - 1;
			}
			initData();
		} else if (actionFlip == ACTION_FLIP_TO_LEFT) {
			curPage++;
			if (curPage > totalCount - 1) {
				curPage = 0;
			}
			initData();
		}
	}

	private int getAction() {
		int actionFlip = ACTION_FLIP_NONE;
		int deltaY = Math.abs(y2 - y1);
		int deltaX = Math.abs(x2 - x1);
		boolean directionY = false;// Y轴移动距离长
		if (!(deltaY > MIN_DELTA_FLIPPED || deltaX > MIN_DELTA_FLIPPED)) {
			return actionFlip;
		}
		if (deltaY > deltaX) {
			directionY = true;
		}

		if (directionY) {// 上下滑动
			return actionFlip;
		}
		actionFlip = getLeftOrRightActionFlip();
		return actionFlip;
	}

	private int getLeftOrRightActionFlip() {
		int actionFlip;
		// UP
		if (y1 > y2) {
			if (x1 > x2) {// LEFT
				actionFlip = ACTION_FLIP_TO_LEFT;
			} else {// RIGHT
				actionFlip = ACTION_FLIP_TO_RIGHT;
			}
		} else {// DOWN
			if (x1 > x2) {// LEFT
				actionFlip = ACTION_FLIP_TO_LEFT;
			} else {// RIGHT
				actionFlip = ACTION_FLIP_TO_RIGHT;
			}
		}
		return actionFlip;
	}

	String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
	private String string;
	private Exams exams;
	private int type;
	private SimulatedTests data;
	private List<TestPapers> subList;
	private List<TestPapers2> subList2;
	private int savedAnswer;
	private HashMap<String, AllExams> allExamsMap;
	private boolean isBack;

	private void initCheckBoxContainer() {
		checkboxContainer.removeAllViews();
		for (int i = 0; i < 5; i++) {
			LinearLayout child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_check, null);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
			child.setLayoutParams(params);
			TextView textView = (TextView) child.findViewById(R.id.ABCD);
			final RadioButton radioButton = (RadioButton) child.findViewById(R.id.checkbox);
			if (savedAnswer == i) {
				radioButton.setChecked(true);
			} else {
				radioButton.setChecked(false);
			}
			textView.setText(letter[i]);
			radioButton.setTag(i);
			radioButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int index = (Integer) radioButton.getTag();
					doTextView.setText("已做");
					if (allExams == null) {
						allExams = new AllExams();
						allExams.setQuestions(exams.getQuestions());
					}
					allExams.setSavedAnswer(index);
					allExamsMap.put(exams.getQuestions(), allExams);
					for (int i = 0; i < 5; i++) {
						LinearLayout childAt = (LinearLayout) checkboxContainer.getChildAt(i);
						RadioButton childAt2 = (RadioButton) childAt.getChildAt(1);
						childAt2.setChecked(false);
					}
					LinearLayout childAt = (LinearLayout) checkboxContainer.getChildAt(index);
					RadioButton childAt2 = (RadioButton) childAt.getChildAt(1);
					childAt2.setChecked(true);
					flag = false;
					rightAnswerTextView.setVisibility(View.VISIBLE);
					rightAnswerTipTextView.setVisibility(View.VISIBLE);
					answerTipLinearLayout.setVisibility(View.VISIBLE);
					anwserTextView.setVisibility(View.VISIBLE);
				}
			});
			checkboxContainer.addView(child);
		}
	}

	private void initView() {
		allExamsMap = new HashMap<String, AllExams>();
		countTextView = (TextView) findViewById(R.id.count);
		doTextView = (TextView) findViewById(R.id.nodo_do);
		saveTextView = (TextView) findViewById(R.id.save);
		jiexiTextView = (TextView) findViewById(R.id.jiexi);
		anwserTextView = (TextView) findViewById(R.id.anwser);
		titleTypeTextView = (TextView) findViewById(R.id.title_type);
		answerTipLinearLayout = (LinearLayout) findViewById(R.id.answer_tip);
		rightAnswerTextView = (TextView) findViewById(R.id.right_answer);
		rightAnswerTipTextView = (TextView) findViewById(R.id.right_answer_tip);
		checkboxContainer = (LinearLayout) findViewById(R.id.checkbox_container);
		titleContentTextView = (TextView) findViewById(R.id.title_content);
		titleTextView = (TextView) findViewById(R.id.title);
		arrowImageView = (ImageView) findViewById(R.id.arrow);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.arrow:
			// BACK
			isBack = true;
			if (allExamsMap.isEmpty()) {
				finish();
			} else {
				saveDialog();
			}
			break;
		case R.id.jiexi:
			// 点击解析button
			if (flag) {
				answerTipLinearLayout.setVisibility(View.VISIBLE);
				anwserTextView.setVisibility(View.VISIBLE);
				rightAnswerTextView.setVisibility(View.VISIBLE);
				rightAnswerTipTextView.setVisibility(View.VISIBLE);
				flag = false;
			} else {
				flag = true;
				rightAnswerTextView.setVisibility(View.GONE);
				rightAnswerTipTextView.setVisibility(View.GONE);
				answerTipLinearLayout.setVisibility(View.GONE);
				anwserTextView.setVisibility(View.GONE);
			}

			break;
		case R.id.save:
			// 点击savebutton
			if (allExamsMap.isEmpty()) {
				Toast.makeText(this, "无修改数据", Toast.LENGTH_SHORT).show();
			} else {
				saveDialog();
			}
			break;

		default:
			break;
		}
	}

	private void saveDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("是否保存答题进度并且退出？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 保存数据
				ArrayList<AllExams> all = new ArrayList<AllExams>();
				Set<Entry<String, AllExams>> entrySet = allExamsMap.entrySet();
				for (Entry<String, AllExams> entry : entrySet) {
					all.add(entry.getValue());
				}
				allExamsDao.save(all);
				dialog.dismiss();
				if (isBack)
					finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (isBack)
					finish();
			}
		});
		dialog = builder.create();
		dialog.show();
	}
}
