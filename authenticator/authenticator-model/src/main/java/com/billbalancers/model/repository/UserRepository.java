package com.billbalancers.model.repository;

import com.billbalancers.model.UserData;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData,Long> {
    boolean existsUserByEmail(String email);
    UserData findPasswordByEmail(String email);
    UserData findUserDataByEmail(String email);

    UserData findByEmail(String email);
}
