package com.example.major.help;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Table.TABLE_SIMULATEDTESTS)
public class SimulatedTests {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField()
	private String majorm_name;// 名字
	@DatabaseField
	private String exam_name;// 试卷名字
	@DatabaseField
	private String subject_name;// tab名字
	@DatabaseField
	private String exam_id;// 试卷id
	@DatabaseField
	private String subject_id;// tab id
	@DatabaseField
	private int exam_type;// 试卷类型
	@DatabaseField
	private boolean is_first;// 是否是第一个
	@DatabaseField
	private int total_time;// 总个数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMajorm_name() {
		return majorm_name;
	}

	public void setMajorm_name(String majorm_name) {
		this.majorm_name = majorm_name;
	}

	public String getExam_name() {
		return exam_name;
	}

	public void setExam_name(String exam_name) {
		this.exam_name = exam_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getExam_id() {
		return exam_id;
	}

	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public int getExam_type() {
		return exam_type;
	}

	public void setExam_type(int exam_type) {
		this.exam_type = exam_type;
	}

	public boolean isIs_first() {
		return is_first;
	}

	public void setIs_first(boolean is_first) {
		this.is_first = is_first;
	}

	public int getTotal_time() {
		return total_time;
	}

	public void setTotal_time(int total_time) {
		this.total_time = total_time;
	}

}
