package com.project.service;

import com.project.domain.Info;

import java.util.Optional;

public interface AuthService {
    public Optional login(Info param) throws Exception;
    public int join(Info param) throws Exception;
    public Optional duplicate(String param) throws Exception;
    public String mailCheck(Info info) throws Exception;
    public int Certification(Info info) throws Exception;
    public Optional findPassword(String param) throws Exception;
    public int changePassword(Info param) throws Exception;
}
