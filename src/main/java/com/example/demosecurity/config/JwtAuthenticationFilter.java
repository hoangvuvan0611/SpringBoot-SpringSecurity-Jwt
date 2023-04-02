package com.example.demosecurity.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {     //khi có mã token gửi yêu cầu tới nó sẽ check xem mã có hợp lệ không
    private static final String Secret_Key="123";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Lấy phần header của request gửi vào
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                //Lấy chuỗi token từ dãy bắt đầu bằng Bearer gửi vào
                String token = authorizationHeader.substring("Bearer ".length()); //Ngắt lấy chuôỗi token con phía sau bearer

                //tạo đói tượng có thuật toán giải mã toke
                Algorithm algorithm = Algorithm.HMAC256(Secret_Key.getBytes()); // so sánh với token được đưa vào xem có khớp nhau không
                DecodedJWT decodedJWT;
                try {
                    //Tạo Trình xác minh tiến hành thuật toán mã hóa
                    JWTVerifier verifier = JWT.require(algorithm).build();

                    //Giải mã token
                    //method vedifiver token
                    decodedJWT = verifier.verify(token);
                }catch (Exception ex) {
                    throw new RuntimeException("Token is error!");
                }

                //Lấy user name từ chuỗi token vừa giải mã
                String username = decodedJWT.getSubject();

                //Lấy danh sách role trong chuỗi token vừa giải mã
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                //Tạo một collection chứa danh sách role
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                //Thêm các role vào danh sách
                Arrays.stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                //Tạo một chuỗi token mới
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                //với mỗi user sẽ có quyền khác nhau
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                filterChain.doFilter(request, response);
            }catch (Exception ex){
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }

//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request,response);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        //List of allowed origins
//        List<String> incomingURLs = Arrays.asList(getServletContext().getInitParameter("incomingURLs").trim().split(","));
//
//
//        // Get client's origin
//        String clientOrigin = request.getHeader("origin");
//
//        // Get client's IP address
//        String ipAddress = request.getHeader("x-forwarded-for");
//        if (ipAddress == null) {
//            ipAddress = request.getRemoteAddr();
//        }
//
//        String userId = request.getParameter("userId").trim();
//        String password = request.getParameter("password").trim();
//
//        PrintWriter out = response.getWriter();
//        response.setContentType("text/html");
//        response.setHeader("Cache-control", "no-cache, no-store");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "-1");
//
//        int myIndex = incomingURLs.indexOf(clientOrigin);
//        //if the client origin is found in our list then give access
//        //if you don't want to check for origin and want to allow access
//        //to all incoming request then change the line to this
//        //response.setHeader("Access-Control-Allow-Origin", "*");
//        if(myIndex != -1){
//            response.setHeader("Access-Control-Allow-Origin", clientOrigin);
//            response.setHeader("Access-Control-Allow-Methods", "POST");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//            response.setHeader("Access-Control-Max-Age", "86400");
//        }
//
//        //just a fake login - if userId is same as password allow access
//        if(userId.equalsIgnoreCase(password)){
//
//            JsonObject myObj = new JsonObject();
//            myObj.addProperty("success", true);
//            out.println(myObj.toString());
//
//        }
//        else {
//
//            JsonObject myObj = new JsonObject();
//            myObj.addProperty("success", false);
//            out.println(myObj.toString());
//
//        }
//
//        out.close();
//
//    }
}
