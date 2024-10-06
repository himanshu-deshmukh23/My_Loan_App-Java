package in.myloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.myloan.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUserId(int userId);
    List<Loan> findByUserIdAndStatus(int userId, String status);
}