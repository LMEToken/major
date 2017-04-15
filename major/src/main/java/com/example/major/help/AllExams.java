package com.example.major.help;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Table.TABLE_ALLEXAMS)
public class AllExams {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String Questions;// �����ѡ��
	@DatabaseField
	private int savedAnswer = -1;// ����Ĵ�
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestions() {
		return Questions;
	}

	public void setQuestions(String questions) {
		Questions = questions;
	}

	public int getSavedAnswer() {
		return savedAnswer;
	}

	public void setSavedAnswer(int savedAnswer) {
		this.savedAnswer = savedAnswer;
	}

}
