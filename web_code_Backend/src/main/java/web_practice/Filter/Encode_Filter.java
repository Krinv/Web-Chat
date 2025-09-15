package web_practice.Filter;



import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


//解决所有乱码问题的过滤器
@WebFilter("/*")
public class Encode_Filter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("乱码问题过滤器已经启动！");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //实现过滤乱码问题代码

        //转为HttpServlet对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置请求的字符编码
        request.setCharacterEncoding("UTF-8");

        //设置相应的请求编码
        // 获取请求头中的 Accept 字段
        String acceptHeader = request.getHeader("Accept");

        // 根据 Accept 字段判断响应类型
        if (acceptHeader != null && acceptHeader.contains("application/json")) {
            // 客户端要求 JSON 格式响应
            response.setContentType("application/json;charset=UTF-8");
        } else {
            // 默认返回 HTML 格式响应
            response.setContentType("text/html;charset=UTF-8");
        }

        //放行
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}

