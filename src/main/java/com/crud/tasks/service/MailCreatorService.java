package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit webside");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "There was a change in your Trello Boards!");
        context.setVariable("company_name", adminConfig.getCompanyDetails());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildNumberOfTasksEmail (String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("trello_url", "https://trello.com/");
        context.setVariable("button", "Visit webside");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("preview_message", "See how many tasks you've got in your Trello");
        return templateEngine.process("mail/daily-about-number-of-tasks-in-Trello", context);
    }
}
