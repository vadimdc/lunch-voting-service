package task.service.lvs.controller;

import task.service.lvs.dto.MessageDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class ApiController
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected MessageSource messageSource;

    protected MessageDto message(String code)
    {
        return message(code, null);
    }

    protected MessageDto message(String code, Object... vars)
    {
        return new MessageDto(messageSource.getMessage(code, vars, LocaleContextHolder.getLocale()));
    }
}
