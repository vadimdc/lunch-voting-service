package task.service.lvs.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = ApiController.class)
public class CurrentUserControllerAdvice
{
    @ModelAttribute("username")
    public String getUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return  ((authentication == null) || !UserDetails.class.isAssignableFrom(authentication.getPrincipal().getClass()))
                ? null
                : ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
