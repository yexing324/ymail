package org.ymail.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.ymail.util.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息传输过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {
    private final StringRedisTemplate stringRedisTemplate;
    private static final List<String> IGNORE_URI = List.of(
            "/ymail/admin/login",
            "/ymail/admin/register",
            "/ymail/upload/getImg"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        //特定链接跳过
        if (!IGNORE_URI.contains(requestURI)) {
            String method = httpServletRequest.getMethod();
            Cookie[] cookies = httpServletRequest.getCookies();
            String token = null, mail = null;
            //cookie里可能携带其他很多值
            try {
                token = Arrays.stream(cookies)
                        .filter(c -> "cookie".equals(c.getName()))
                        .findFirst()
                        .map(Cookie::getValue)
                        .get();
                mail = Arrays.stream(cookies)
                        .filter(c -> "mail".equals(c.getName()))
                        .findFirst()
                        .map(Cookie::getValue)
                        .get();
            } catch (Exception e) {
                log.warn("未携带cookies");
            }
            if (StringUtil.isBlank(token) || StringUtil.isBlank(mail)) {
                returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Result.failure("token验证失败")));
                return;
            }
            try {
                Object userJson = stringRedisTemplate.opsForHash().get("login_account:" + mail, token);
                Optional.ofNullable(userJson).ifPresentOrElse(u -> {
                            UserInfoDTO curUser = JSON.parseObject(userJson.toString(), UserInfoDTO.class);
                            UserContext.setUser(curUser);
                        }, () -> {
                            //为空
                            returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Result.failure("token验证失败")));
                            return;
                        }
                );
            } catch (Exception e) {
                returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Result.failure("token验证失败")));
                return;
            }
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }

    private void returnJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
        } catch (IOException e) {
            log.warn("获得writer异常");
        }
    }
}