package web_practice.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")  // 设置过滤器适用于所有请求
public class cors_filter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("跨域问题过滤器已启动");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 获取请求来源域（Origin）
        String origin = httpRequest.getHeader("Origin");

        // 设置 CORS 响应头
        if (origin != null) {
            httpResponse.setHeader("Access-Control-Allow-Origin", origin); // 根据请求来源动态设置
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");  // 支持携带认证信息
            httpResponse.setHeader("Access-Control-Expose-Headers", "Authorization");  // 暴露的头部
        }

        // 处理 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;  // 结束过滤器链，直接返回
        }

        // 继续执行请求链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("跨域问题过滤器已销毁");
    }
}
