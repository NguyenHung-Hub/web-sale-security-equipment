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

    @Query(value = " create event ?1 on schedule at current_timestamp + interval 7 day do begin " +
            "delete from users where email = ?2; " +
            "drop event ?1; " +
            "end ", nativeQuery = true)
    public void registerEvent(String nameEvent, String emailEvent);

    @Modifying
    @Query(value = "update users set enable = true, verification_code = null where user_id = :id", nativeQuery = true)
    public void enable(@Param("id") long id);

    @Modifying
    @Query(value = "update users set password = :password where user_id = :id", nativeQuery = true)
    public void updatePassword(@Param("id") long id, @Param("password") String password);

    @Modifying
    @Query(value = "update users set verification_code = :verification_code where user_id = :id", nativeQuery = true)
    public void updateVerificationCode(@Param("verification_code") String code, @Param("id") long id);
}
