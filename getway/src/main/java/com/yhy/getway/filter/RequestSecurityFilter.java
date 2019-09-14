package com.yhy.getway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description: Zuul请求日志过滤器
* @Author: yaohongyu
* @Date: 2019/9/12
*/
//@Component
public class RequestSecurityFilter extends ZuulFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestSecurityFilter.class);

  @Override
  public String filterType() {
    return FilterConstants.POST_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletResponse response = ctx.getResponse();
    if (HttpStatus.OK.value() == response.getStatus()) {
      // 正常请求，直接退出
      return null;
    }
    HttpServletRequest request = ctx.getRequest();
    if (StringUtils.equals(request.getRequestURI(), "/error")) {
      // 异常处理，自动忽略.
      return null;
    }
    LOGGER.debug(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
    LOGGER.debug(String.format("response %d", response.getStatus()));
    if (HttpStatus.UNAUTHORIZED.value() == response.getStatus()) {
      try {
        response.sendRedirect("/error" + "?detail=401");
      } catch (IOException e) {
        LOGGER.error("Error for common error page!", e);
      }
    }
    return null;
  }
}
