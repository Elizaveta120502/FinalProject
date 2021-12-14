package com.epam.jwd.tag;

import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class WelcomeUserTag extends TagSupport {


    private static final long serialVersionUID = -7903586273116383803L;

    private static final String PARAGRAPH_TAGS = "<p>%s, %s</p>";
    private static final String USER_SESSION_PARAM_NAME = "user";

    private String text;

    @Override
    public int doStartTag() {
        extractUserNameFromSession()
                .ifPresent(name -> printTextToOut(format(PARAGRAPH_TAGS, text, name)));
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private Optional<String> extractUserNameFromSession() {
        return Optional.ofNullable(pageContext.getSession())
                .map(session -> (Account) session.getAttribute(USER_SESSION_PARAM_NAME))
                .map(Account::getLogin);
    }

    private void printTextToOut(String text) {
        final JspWriter out = pageContext.getOut();
        try {
            out.write(text);
        } catch (IOException e) {
            LoggerProvider.getLOG().error("error occurred writing text to jsp. text: {}, tagId: {}", text, id);
            LoggerProvider.getLOG().error(e);
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}