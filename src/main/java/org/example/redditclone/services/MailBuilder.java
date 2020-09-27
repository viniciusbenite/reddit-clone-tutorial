package org.example.redditclone.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailBuilder {
    
    private final TemplateEngine templateEngine;

    /**
     * Create an e-mail for user verification/validationb on signup.
     * @param String msg
     * @return thymeleaf email template
     */
    public String build(String msg) {
        Context ctx = new Context();
        ctx.setVariable("message", msg);

        return templateEngine.process("mailTemplate", ctx);
    }
}
