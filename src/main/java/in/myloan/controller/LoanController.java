package in.myloan.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import in.myloan.entity.Loan;
import in.myloan.entity.Repayment;
import in.myloan.entity.User;
import in.myloan.repository.LoanRepository;
import in.myloan.repository.RepaymentRepository;
import in.myloan.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
public class LoanController {

	@Autowired
	UserRepository urepo;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private RepaymentRepository repaymentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute User user) {
		urepo.save(user);
		return "login";
	}

	@GetMapping("/adminProfile")
	public ModelAndView adminProfile() {
		ModelAndView mv = new ModelAndView();
		List<Loan> loans = loanRepository.findAll();
		mv.addObject("loans", loans);
		mv.setViewName("adminProfile");
		return mv;
	}

	@RequestMapping("/userProfile")
	public ModelAndView userProfile(@RequestParam("userid") int userId) {
		ModelAndView mv = new ModelAndView();
		User user = urepo.findByUserId(userId);
		List<Loan> loans = loanRepository.findByUserId(user.getUserId());
		List<Repayment> repayments = repaymentRepository.findByUserId(user.getUserId());
		mv.addObject("user", user);
		mv.addObject("loans", loans);
		mv.addObject("repayments", repayments);
		mv.setViewName("userProfile");
		return mv;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam String email, @RequestParam String password) {
		System.err.println("Login method called!");
		User user = urepo.findByEmail(email);
		ModelAndView mv = new ModelAndView();

		if (email.equals("admin@myloan") && password.equals("admin123")) {
			System.err.println("Admin profile match!");
			return adminProfile();
		} else if (user != null && user.getPassword().equals(password)) {
			System.err.println("credential matches!");
			return userProfile(user.getUserId());
		} else {
			System.err.println("credential does not match!");
			mv.setViewName("home");
			return mv;
		}
	}

	@PostMapping("/applyLoan")
	public ModelAndView applyLoan(@RequestParam("loanAmount") double loanAmount,
			@RequestParam("durationMonths") int durationMonths, @RequestParam("userId") int userId) {
		System.err.println("Apply loan method called!");
		Loan loan = new Loan(userId, loanAmount, durationMonths, "pending");
		loanRepository.save(loan);

		return userProfile(userId);
	}

	@PostMapping("/rejectLoan")
	public String rejectLoan(@RequestParam int loanId) {
		Loan loan = loanRepository.findById(loanId).orElse(null);
		if (loan != null && loan.getStatus().equalsIgnoreCase("Pending")) {
			loan.setStatus("Rejected");
			loanRepository.save(loan);
		}
		return "redirect:/adminProfile";
	}
	
	@PostMapping("/deleteLoan")
	public String deleteLoan(@RequestParam int loanId) {
		loanRepository.deleteById(loanId);
		return "redirect:/adminProfile";
	}

	@PostMapping("/approveLoan")
	public String approveLoan(@RequestParam int loanId) {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		Loan loan = loanRepository.findById(loanId).orElse(null);
			if (loan != null && loan.getStatus().equalsIgnoreCase("Pending")) {
				loan.setStatus("Approved");
				loanRepository.save(loan);

				createRepayments(loan);
			}
		return "redirect:/adminProfile";
	}

	private void createRepayments(Loan loan) {
		int numberOfRepayments = loan.getDurationMonths();
		BigDecimal repaymentAmount = new BigDecimal(loan.getLoanAmount()).divide(new BigDecimal(numberOfRepayments),
				RoundingMode.CEILING);

		for (int i = 0; i < numberOfRepayments; i++) {
			Repayment repayment = new Repayment();
			repayment.setUserId(loan.getUserId());
			repayment.setLoanId(loan.getLoanId());
			repayment.setAmount(repaymentAmount.doubleValue());
			repayment.setDueDate(calculateDueDate(i));
			repayment.setRepaymentStatus("Pending");
			repaymentRepository.save(repayment);
		}
	}

	@PostMapping("/payRepayment")
	public ModelAndView payRepayment(@RequestParam int repaymentId) {
		Repayment repayment = repaymentRepository.findById(repaymentId).orElse(null);
		if (repayment != null) {
			repayment.setRepaymentStatus("paid");
			repaymentRepository.save(repayment);

			// Check if all repayments for the loan are paid
			List<Repayment> repayments = repaymentRepository.findByLoanId(repayment.getLoanId());
			boolean allPaid = repayments.stream().allMatch(r -> r.getRepaymentStatus().equalsIgnoreCase("paid"));
			if (allPaid) {
				Loan loan = loanRepository.findById(repayment.getLoanId()).orElse(null);
				if (loan != null) {
					loan.setStatus("ALL DUES PAID");
					loanRepository.save(loan);
				}
			}
		}

		return userProfile(repayment.getUserId());
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	private Date calculateDueDate(int monthsFromNow) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, monthsFromNow);
		return calendar.getTime();
	}
}
