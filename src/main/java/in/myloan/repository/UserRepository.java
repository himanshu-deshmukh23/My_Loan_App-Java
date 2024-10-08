package in.myloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.myloan.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);
	User findByUserId(int userId);
}