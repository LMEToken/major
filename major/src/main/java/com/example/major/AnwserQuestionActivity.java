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
import com.example.major.help.ExamsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class AnwserQuestionActivity extends Activity implements OnClickListener {

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
		int ints = intent.getIntExtra(AnwserQuestionActivity.class.getSimpleName(), -1);
		string = intent.getStringExtra("name");
		List<Exams> exams = ExamsDao.newInstance(this).readUnitsModel();
		exam = new ArrayList<Exams>();
		if (exams != null && !exams.isEmpty()) {
			HashMap<Integer, ArrayList<Exams>> maps = new HashMap<Integer, ArrayList<Exams>>();
			ArrayList<String> charpterNames = new ArrayList<String>();
			int mPid = -1;
			for (int i = 0; exams.size() > i; i++) {
				Exams unit = exams.get(i);
				int pid = unit.getPid();
				if (i == 0)
					mPid = pid;
				if (!charpterNames.contains(pid + "")) {
					ArrayList<Exams> ids = new ArrayList<Exams>();
					ids.add(unit);
					maps.put(pid, ids);
					charpterNames.add(pid + "");
				} else {
					maps.get(pid).add(unit);
				}
			}
			if (ints != -1) {
				exam.addAll(maps.get(ints + mPid));
			}
		}

	}

	private void initData() {
		exams = exam.get(curPage);
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
		int totalCount = exam.size();
		countTextView.setText(curPage + 1 + "/" + totalCount);
		anwserTextView.setText(exams.getAnswer());
		rightAnswerTextView.setText(exams.getAnalysis());
		if (savedAnswer != -1) {
			doTextView.setText("????");
			rightAnswerTextView.setVisibility(View.VISIBLE);
			rightAnswerTipTextView.setVisibility(View.VISIBLE);
			anwserTextView.setVisibility(View.VISIBLE);
			answerTipLinearLayout.setVisibility(View.VISIBLE);
			flag = true;
		} else {
			doTextView.setText("????");
			rightAnswerTextView.setVisibility(View.GONE);
			rightAnswerTipTextView.setVisibility(View.GONE);
			anwserTextView.setVisibility(View.GONE);
			answerTipLinearLayout.setVisibility(View.GONE);
			flag = false;
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
	private int curPage = 0; // ????????????view????????id
	private static int MIN_DELTA_FLIPPED = 80;
	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ????????????????
			x1 = (int) event.getX();
			y1 = (int) event.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			// ????????????????
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
				curPage = exam.size() - 1;
			}
			initData();
		} else if (actionFlip == ACTION_FLIP_TO_LEFT) {
			curPage++;
			if (curPage > exam.size() - 1) {
				curPage = 0;
			}
			initData();
		}
	}

	private int getAction() {
		int actionFlip = ACTION_FLIP_NONE;
		int deltaY = Math.abs(y2 - y1);
		int deltaX = Math.abs(x2 - x1);
		boolean directionY = false;// Y????????????
		if (!(deltaY > MIN_DELTA_FLIPPED || deltaX > MIN_DELTA_FLIPPED)) {
			return actionFlip;
		}
		if (deltaY > deltaX) {
			directionY = true;
		}

		if (directionY) {// ????????
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
	private ArrayList<Exams> exam;
	private String string;
	private Exams exams;
	private int savedAnswer = -1;
	private AllExamsDao allExamsDao;
	private AllExams allExams;
	private HashMap<String, AllExams> allExamsMap;
	private boolean isBack;
	private boolean hasSaved;

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
					doTextView.setText("????");
					if (allExams == null) {
						allExams = new AllExams();
						allExams.setQuestions(exam.get(curPage).getQuestions());
					}
					allExams.setSavedAnswer(index);
					allExamsMap.put(exam.get(curPage).getQuestions(), allExams);
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
			isBack = true;
			if (allExamsMap.isEmpty()) {
				finish();
			} else {
				saveDialog();
			}
			break;
		case R.id.jiexi:
			// ????????button
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
			if (allExamsMap.isEmpty()) {
				Toast.makeText(this, "??????????", Toast.LENGTH_SHORT).show();
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
		builder.setTitle("????");
		builder.setMessage("??????????????????????????");
		builder.setPositiveButton("????", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
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
		builder.setNegativeButton("????", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (isBack) {
					finish();
				}
			}
		});
		dialog = builder.create();
		dialog.show();
	}
}
