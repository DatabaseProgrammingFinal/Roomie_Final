package model.domain;

import java.sql.Date;

/**
 * 요청글 확인을 위해 필요한 도메인 클래스. Rental_request_confirm 테이블과 대응됨
 */
public class RequestConfirm {
	private int id;
	private Date actual_return_date;
	private int penalty_points;
	private int overdue_days;
	private int request_post_id; // 외래키
	private int provider_id; // 외래키
	
	public RequestConfirm() {};
	
	public RequestConfirm(int id, Date actual_return_date, int penalty_points, int overdue_days, int request_post_id, int provider_id) {
		super();
		this.id = id;
		this.actual_return_date = actual_return_date;
		this.penalty_points = penalty_points;
		this.overdue_days = overdue_days;
		this.request_post_id = request_post_id;
		this.provider_id = provider_id;
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

	public int getRequest_post_id() {
		return request_post_id;
	}

	public void setRequest_post_id(int request_post_id) {
		this.request_post_id = request_post_id;
	}

	public int getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(int provider_id) {
		this.provider_id = provider_id;
	}

	@Override
	public String toString() {
		return "RequestConfirm [id=" + id + ", actual_return_date=" + actual_return_date + ", penalty_points="
				+ penalty_points + ", overdue_days=" + overdue_days + ", request_post_id=" + request_post_id
				+ ", provider_id=" + provider_id + ", getId()=" + getId() + ", getActual_return_date()="
				+ getActual_return_date() + ", getPenalty_points()=" + getPenalty_points() + ", getOverdue_days()="
				+ getOverdue_days() + ", getRequest_post_id()=" + getRequest_post_id() + ", getProvider_id()="
				+ getProvider_id() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
