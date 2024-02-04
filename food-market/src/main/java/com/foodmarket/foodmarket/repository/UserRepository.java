package com.foodmarket.foodmarket.repository;

import com.foodmarket.foodmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Query(value = "select u.id, u.name, u.email, u.address, u.house_number, u.phone_number, u.city from users u LIMIT 10 OFFSET 10",
            nativeQuery = true)
    List<Object[]> getUserLimit();
}
