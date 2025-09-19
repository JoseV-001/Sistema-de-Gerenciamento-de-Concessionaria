// Em config/NoCacheFilter.java
package com.meuPI.gestao_concessionaria.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Define alta prioridade para que este filtro rode antes dos outros.
public class NoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) res;

        // Adiciona os cabeçalhos que instruem o navegador a não fazer cache
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpResponse.setDateHeader("Expires", 0); // Proxies.

        chain.doFilter(req, res);
    }
}
