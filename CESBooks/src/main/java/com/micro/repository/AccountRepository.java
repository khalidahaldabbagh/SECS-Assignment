package com.micro.repository;

import com.micro.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>
{
Optional<Account> findByStudentId(String studentID);
}
