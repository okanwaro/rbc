package com.rbc.stocks.http;

import com.rbc.stocks.errors.ErrorResponse;

import com.rbc.stocks.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@PropertySource({"classpath:application.properties"})
@Component
public class CustomHttpInterceptor implements HandlerInterceptor {
    Logger log =  LoggerFactory.getLogger(CustomHttpInterceptor.class);
    @Value("${X_CLIENT_ID}")
    String clientID;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ((request.getHeader("X_CLIENT_ID") == null) || !(request.getHeader("X_CLIENT_ID").equals(clientID)) ){
//            response.setStatus(404);
            log.info("jhbhkj56789"+ clientID);
           response.setStatus(404);
           response.getWriter().println(" Client id is not validated");
             return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
