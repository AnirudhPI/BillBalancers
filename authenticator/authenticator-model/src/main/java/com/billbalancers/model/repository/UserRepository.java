package com.billbalancers.model.repository;

import com.billbalancers.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData,Long> {
    boolean existsUserByEmail(String email);
//    void insertData(String email,String password,String first_name,String last_name);

    String findPasswordByEmail(String email);
}
