package com.unifyed.attendance.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class UnifyedTenantInterceptor extends HandlerInterceptorAdapter {


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //OAuth2AccessToken token = tokenservice.readAccessToken(request.getParameter("access_token"));
        String tenantId = request.getHeader("X-TENANT-ID");//(String) token.getAdditionalInformation().get("X-TENANT-ID");
        String principal_user = request.getHeader("principal-user");
        if (tenantId == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        } else {
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(tenantId);
        }
        return super.preHandle(request, response, handler);
    }
}
