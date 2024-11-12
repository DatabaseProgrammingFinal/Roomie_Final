package model.domain;

import java.sql.Date;

/**
 * 제공글 확인을 위해 필요한 도메인 클래스. Rental_provide_confirm 테이블과 대응됨
 */
public class ProvideConfirm {
	private int id;
	private Date actual_return_date;
	private int penalty_points;
	private int overdue_days;
	private int provide_post_id; // 외래키
	private int requester_id; // 외래키
	
	public ProvideConfirm() {};
	
	public ProvideConfirm(int id, Date actual_return_date, int penalty_points, int overdue_days, int provide_post_id, int requester_id) {
		super();
		this.id = id;
		this.actual_return_date = actual_return_date;
		this.penalty_points = penalty_points;
		this.overdue_days = overdue_days;
		this.provide_post_id = provide_post_id;
		this.requester_id = requester_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getActual_return_date() {
		return actual_return_date;
	}
	public void setActual_return_date(Date actual_return_date) {
		this.actual_return_date = actual_return_date;
	}
	public int getPenalty_points() {
		return penalty_points;
	}
	public void setPenalty_points(int penalty_points) {
		this.penalty_points = penalty_points;
	}
	public int getOverdue_days() {
		return overdue_days;
	}
	public void setOverdue_days(int overdue_days) {
		this.overdue_days = overdue_days;
	}
	public int getProvide_post_id() {
		return provide_post_id;
	}
	public void setProvide_post_id(int provide_post_id) {
		this.provide_post_id = provide_post_id;
	}
	public int getRequester_id() {
		return requester_id;
	}
	public void setRequester_id(int requester_id) {
		this.requester_id = requester_id;
	}
	@Override
	public String toString() {
		return "ProvideConfirm [id=" + id + ", actual_return_date=" + actual_return_date + ", penalty_points="
				+ penalty_points + ", overdue_days=" + overdue_days + ", provide_post_id=" + provide_post_id
				+ ", requester_id=" + requester_id + ", getId()=" + getId() + ", getActual_return_date()="
				+ getActual_return_date() + ", getPenalty_points()=" + getPenalty_points() + ", getOverdue_days()="
				+ getOverdue_days() + ", getProvide_post_id()=" + getProvide_post_id() + ", getRequester_id()="
				+ getRequester_id() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
