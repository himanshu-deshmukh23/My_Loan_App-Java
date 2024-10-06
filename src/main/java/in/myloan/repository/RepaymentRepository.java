package in.myloan.repository;

import in.myloan.entity.Repayment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {
	List<Repayment> findByUserId(int userId);
	List<Repayment> findByLoanId(int loanId);
}
