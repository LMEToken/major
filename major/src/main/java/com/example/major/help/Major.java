package com.example.major.help;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

@DatabaseTable(tableName = "表名")
public class Major {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField()
	private int orderId = 10;// 第几题
	@DatabaseField
	private String subType = "A1";// 子类型--A1 OR A2
	@DatabaseField
	private int majorType;// 什么练习
	@DatabaseField
	private String title = "SDGFSDLG";
	@DatabaseField
	private int answer = 2;// 正确答案
	@DatabaseField
	private int savedAnswer = 1;// 保存的答案
	@DatabaseField
	private boolean isSaved = true;// 是否保存了,标志已经做过了
	@DatabaseField
	private ArrayList<String> answers = new ArrayList<String>();// 是否保存了,标志已经做过了

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public int getMajorType() {
		return majorType;
	}

	public void setMajorType(int majorType) {
		this.majorType = majorType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getSavedAnswer() {
		return savedAnswer;
	}

	public void setSavedAnswer(int savedAnswer) {
		this.savedAnswer = savedAnswer;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

}
