package com.billbalancers.authenticator.controller;

import com.billbalancers.authenticatorapi.api.StudentApi;
import com.billbalancers.authenticatorapi.model.Student;
import org.springframework.http.ResponseEntity;

public class RestController implements StudentApi {

    @Override
    public ResponseEntity<Student> getStudentDetail() {
        return null;
    }
}
