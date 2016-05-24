package org.openlmis.example.web;

import org.openlmis.example.util.ServiceSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ServiceNameController {

    @Autowired
    private MessageSource messageSource;
    
    @RequestMapping("/")
    public ServiceSignature index() {
        return new ServiceSignature(ServiceSignature.SERVICE_NAME, ServiceSignature.SERVICE_VERSION);
    }

    @RequestMapping("/hello")
    public String test() {
        return messageSource.getMessage("message.hello.world", null, LocaleContextHolder.getLocale());
    }
}