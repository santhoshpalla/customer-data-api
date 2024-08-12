package com.spalla.customer.api.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spalla.customer.api.entity.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RequestResponseInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        try{
            log.info("Incoming request for API {} {}", request.getMethod(), request.getRequestURI());
            if(log.isDebugEnabled() && (StringUtils.equals(HttpMethod.POST.name(), request.getMethod()) ||
                    StringUtils.equals(HttpMethod.PUT.name(), request.getMethod()))
                    && Objects.nonNull(request.getReader())
                    && Objects.nonNull(request.getReader().lines())){
                String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objMapper = new ObjectMapper();
                Customer customer = objMapper.readValue(payload, Customer.class);
                log.debug("received request payload: {}", customer.toString());
            }
        }catch (Exception ex){
            log.error("failure logging request in interceptor: ",ex);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        try{
            log.info("generated response with status: {}", response.getStatus());
        }catch (Exception ex){
            log.error("failure logging response status in interceptor: ",ex);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws Exception {
        try{
            log.debug("response has been sent");
            if(response.getStatus() >= 300){
                log.info("spring controller advice responded with status: {}", response.getStatus());
            }
            if(Objects.nonNull(ex)){
                log.error("failure to send the response: ",ex);
            }
        }catch (Exception exception){
            log.error("failure in post response logging interceptor: ", exception);
        }
    }
}
