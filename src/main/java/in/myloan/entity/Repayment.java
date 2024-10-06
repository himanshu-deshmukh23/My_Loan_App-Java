package in.myloan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
	private int loanId; // Assuming loanId to link with Loan entity
    private double amount;
    private Date dueDate;
    private String repaymentStatus; // New attribute
	public Repayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Repayment(int id, int userId, int loanId, double amount, Date dueDate, String repaymentStatus) {
		super();
		this.id = id;
		this.userId = userId;
		this.loanId = loanId;
		this.amount = amount;
		this.dueDate = dueDate;
		this.repaymentStatus = repaymentStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	@Override
	public String toString() {
		return "Repayment [id=" + id + ", userId=" + userId + ", loanId=" + loanId + ", amount=" + amount + ", dueDate="
				+ dueDate + ", repaymentStatus=" + repaymentStatus + "]";
	}

    
}
