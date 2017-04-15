package com.example.major.help;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Table.TABLE_EXAMS)
public class Exams {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField()
	private int pid;// ����
	@DatabaseField
	private String Questions;// �����ѡ��
	@DatabaseField
	private String Answers;// ��
	@DatabaseField
	private String Analysis;// ����
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getQuestions() {
		return Questions;
	}

	public void setQuestions(String questions) {
		Questions = questions;
	}

	public String getAnswer() {
		return Answers;
	}

	public void setAnswer(String Answers) {
		this.Answers = Answers;
	}

	public String getAnalysis() {
		return Analysis;
	}

	public void setAnalysis(String analysis) {
		Analysis = analysis;
	}

	public String getAnswers() {
		return Answers;
	}

	public void setAnswers(String answers) {
		Answers = answers;
	}

}
