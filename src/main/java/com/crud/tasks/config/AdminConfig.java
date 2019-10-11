package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {
    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${info.app.company.name}")
    private String companyDetails;
}
