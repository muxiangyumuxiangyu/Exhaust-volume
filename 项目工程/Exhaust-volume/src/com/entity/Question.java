package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class Question {
	private int id;
	private String content;
	private int repeat;
	private int flag;
	private String A;
	private String B;
	private String C;
	private String D;
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	private QuestionLevel level;
	private Chapter chapter;
	private QuestionType type;
	private Answer answer;
	private Set<Recorder> recorders=new HashSet<Recorder>();
	private Set<Sort> sorts=new HashSet<Sort>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="chapter_id")
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRepeat() {
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="level_id")
	public QuestionLevel getLevel() {
		return level;
	}
	public void setLevel(QuestionLevel level) {
		this.level = level;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="type_id")
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	@OneToMany
	public Set<Recorder> getRecorders() {
		return recorders;
	}
	public void setRecorders(Set<Recorder> recorders) {
		this.recorders = recorders;
	}
	
	@OneToMany
	public Set<Sort> getSorts() {
		return sorts;
	}
	public void setSorts(Set<Sort> sorts) {
		this.sorts = sorts;
	}
}
