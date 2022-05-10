package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query(value = "select * from users u where verification_code = :verification_code", nativeQuery = true)
    public User findByVerificationCode(@Param("verification_code") String code);

    @Modifying
    @Query(value = "update users set enable = true where user_id = :id", nativeQuery = true)
    public void enable(@Param("id") long id);
}
