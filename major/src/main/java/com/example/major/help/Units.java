package com.example.major.help;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Comparator;

@DatabaseTable(tableName = Table.TABLE_UNITS)
public class Units implements Comparator<Units> {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField()
	private int chapter;// 章节
	@DatabaseField
	private String chapterName;// 章节名字
	@DatabaseField
	private int units;// 排序
	@DatabaseField
	private String unitsName;
	@DatabaseField
	private int answer;// 回答的数量
	@DatabaseField
	private int unanswer;// 未完成的数量

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getUnanswer() {
		return unanswer;
	}

	public void setUnanswer(int unanswer) {
		this.unanswer = unanswer;
	}

	@Override
	public int compare(Units lhs, Units rhs) {
		return lhs.chapter - rhs.chapter;
	}

}
