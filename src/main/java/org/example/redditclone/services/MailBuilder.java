package org.example.redditclone.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailBuilder {

    /*
     * Create an e-mail for user verification/validationb on signup
     */
    private final TemplateEngine templateEngine;

    public String build(String msg) {
        Context ctx = new Context();
        ctx.setVariable("message", msg);

        return templateEngine.process("mailTemplate", ctx);
    }

    
}
