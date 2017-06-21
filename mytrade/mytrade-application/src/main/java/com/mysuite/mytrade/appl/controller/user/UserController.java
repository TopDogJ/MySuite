package com.mysuite.mytrade.appl.controller.user;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.api.entity.bean.security.Profile;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.bean.user.User;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by jianl on 7/06/2017.
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractLoggable {
    @Autowired
    private EntityRepository<User> userEntityRepository;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;

    public UserController() {
        super(LogFactory.getLog(UserController.class));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody String add(@RequestParam String username, @RequestParam String password, HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (null==cookies) {
            System.out.println("No cookie detacted.");
        } else {
            for(Cookie cookie : cookies){
                System.out.println("name:"+cookie.getName()+",value:"+ cookie.getValue());
            }
        }
//        try {
//            User user = this.userEntityRepository.findByDuplicateForReference(Arrays.asList(username));
//            return "Exists";
//        } catch (EntityNotFoundException e) {
//            User user = new User();
//            user.setUsername(username);
//            user.setPassword(password);
//            this.userEntityRepository.save(user);
//            httpSession.setAttribute("user", httpSession.getId());
//            return "OK";
//        }
        this.getLogger().info("Received reuqest to add user: " + username);
        return username;
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody User logon(@RequestParam String username, @RequestParam String password, @RequestParam boolean rememberMe, HttpServletRequest request, HttpServletResponse response){

        try {
            User user = this.userEntityRepository.findByDuplicateForReference(Arrays.asList(username));
            if(user.getPassword().equals(password)){
                if(rememberMe) {
                    response.addCookie(this.createCookie(username));
                }
                request.getSession().setAttribute("username", user.getUsername());
                return user;
            }else{
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
    }

    @RequestMapping(value = "/security/add", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    List<Security> addSecurity(@RequestParam String securityCode, HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        String username = null;
        for (Cookie cookie: cookies) {
            if(cookie.getName() != null && cookie.getName().equals("mytrade")){
                 username = this.decodeCookieValue(cookie.getValue());
                 this.getLogger().info("Found username from cookie: " + username);
            }
        }
        if(username == null){
            username  = (String) request.getSession().getAttribute("username");
            this.getLogger().info("Found username from session: " + username);
        }

        if(username == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }else{
            try{
                User user = this.find(username);
                try {
                    Security security = this.findSecurity(securityCode);
                    if(user.getSelectedSecurity() == null){
                        List<Security> securityList = Arrays.asList(security);
                        user.setSelectedSecurity(securityList);
                    }else{
                        user.getSelectedSecurity().add(security);
                    }
                    this.update(user);
                    return user.getSelectedSecurity();
                }catch (EntityNotFoundException e){
                    this.getLogger().error("Failed to find security to add for user: " + username + ", securityCode: " + securityCode);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return null;
                }
            }catch (EntityNotFoundException e){
                this.getLogger().error("Failed to find security to add for user: " + username + ", securityCode: " + securityCode);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
        }
    }

    private void update(User user) {
        this.userEntityRepository.update(user);
    }

    private Security findSecurity(String securityCode) throws EntityNotFoundException {
        return this.securityEntityRepository.findByDuplicateForReference(Arrays.asList(securityCode));
    }

    private User find(String username) throws EntityNotFoundException {
        return this.userEntityRepository.findByDuplicate(Arrays.asList(username));
    }

    private String decodeCookieValue(String cookieValue){
        return Base64.getDecoder().decode(cookieValue.getBytes()).toString();
    }

    private Cookie createCookie(String username) {
        Cookie cookie = new Cookie("mytrade", Base64.getEncoder().encode(username.getBytes()).toString());
        cookie.setSecure(true);
        cookie.setPath("/mytrade");
        cookie.setDomain("192.168.3.14");
        return cookie;
    }
}
