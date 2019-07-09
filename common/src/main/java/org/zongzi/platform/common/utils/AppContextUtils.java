package org.zongzi.platform.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppContextUtils implements ApplicationContextAware {

    private static ApplicationContext _appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextUtils._appContext = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        Objects.requireNonNull(AppContextUtils._appContext, "spring ApplicationContext is not initial.");
        return AppContextUtils._appContext.getBean(requiredType);
    }

//    public static ResponseEntity executeGet(String url, String username, String password) {
//        RestTemplate restTemplate = AppContextUtils.getBean(RestTemplate.class);
//        HttpHeaders requestHeaders = new HttpHeaders();
//        if (StringUtils.hasLength(username) && StringUtils.hasLength(password)) {
//            String usernamePassword = username + ":" + password;
//            requestHeaders.set("Authorization", Base64.getEncoder().encodeToString(usernamePassword.getBytes()));
//        }
//        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(requestHeaders), Object.class);
//    }
//
//    public static ResponseEntity executeGet(String url) {
//        return executeGet(url, null, null);
//    }
}
