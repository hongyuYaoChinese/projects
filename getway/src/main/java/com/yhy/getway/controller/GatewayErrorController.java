package com.yhy.getway.controller;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统异常解析控制器类.
 */

@Controller
@RequestMapping(value = "/error")
public class GatewayErrorController implements ErrorController  {

  @Override
  public String getErrorPath() {
    return "error-default";
  }

  /**
   * 页面同步请求的异常处理
   * @param request 请求对象
   * @param response 响应对象
   * @param e 异常信息
   * @return 通用异常页面
   */
  @RequestMapping(produces={"text/html"})
  public ModelAndView error(HttpServletRequest request, HttpServletResponse response, Exception e) {
    Map<String, String> errorInfo = new HashMap<>(4);
    errorInfo.put("timestamp", new Date().toString());
    errorInfo.put("url", request.getRequestURL().toString());
    errorInfo.put("status", String.valueOf(getStatus(request).value()));
    switch (HttpStatus.valueOf(response.getStatus())) {
      case UNAUTHORIZED:
      case FORBIDDEN:
        // 权限不足，需要重新登陆
        errorInfo.put("status", "401");
        errorInfo.put("message", "认证信息过期，请重新登录！");
        break;
      case OK:
        // 子系统异常请求
        errorInfo.put("status", request.getParameter("detail"));
        errorInfo.put("message", this.getDetailMessage(request.getParameter("detail")));
        break;
      default:
        // 其它异常，尚未详细分解
        errorInfo.put("message", StringUtils.isEmpty(e.getMessage()) ? "系统异常，请联系管理员！" : e.getMessage());
        break;
    }
    return new ModelAndView(getErrorPath(), errorInfo);
  }

  private String getDetailMessage(String detailCode) {
    String message = "系统异常，请联系管理员！";
    switch (detailCode) {
      case "401":
        message = "认证信息过期，请重新登录！";
        break;
      default:
        break;
    }
    return message;
  }

  /**
   * 异步请求通用异常信息设定
   * @param request 请求对象
   * @return 响应信息
   */
  @RequestMapping
  @ResponseBody
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request)
  {
    WebRequest webRequest = new ServletWebRequest(request);
    Map body = new DefaultErrorAttributes(true).getErrorAttributes(webRequest, true);
    HttpStatus status = getStatus(request);
    return new ResponseEntity(body, status);
  }

  /**
   * 获得请求响应状态骂
   * @param request 请求对象
   * @return 响应状态骂
   */
  private HttpStatus getStatus(HttpServletRequest request)
  {
    Integer statusCode = (Integer)request
        .getAttribute("javax.servlet.error.status_code");

    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    try
    {
      return HttpStatus.valueOf(statusCode.intValue());
    } catch (Exception ex) {
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
