package com.coding.challenge.app.repository;

import com.coding.challenge.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.firstName = :searchName or u.lastName = :searchName")
    List<User> findUserByFirstNameOrLastName(@Param("searchName") String searchName);
}
