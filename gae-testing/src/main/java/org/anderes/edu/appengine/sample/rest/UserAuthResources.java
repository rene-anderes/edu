package org.anderes.edu.appengine.sample.rest;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("users")
public class UserAuthResources {
    @Context
    private HttpServletRequest request;
    private UserService userService = UserServiceFactory.getUserService();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @GET
    public String getUserId() {
        final String urlPath = UriBuilder.fromPath("/").path(MyApplication.class).path(UserAuthResources.class).build().getPath();
        logger.info("Login URL: '" + userService.createLoginURL(urlPath) + "'");
        logger.info("Logout URL: '" + userService.createLogoutURL(urlPath) + "'");
        if (request.getUserPrincipal() != null) {
            logger.info("current user getUserId: " + userService.getCurrentUser().getUserId());
            logger.info("current user getAuthDomain: " + userService.getCurrentUser().getAuthDomain());
            logger.info("current user isUserLoggedIn: " + userService.isUserLoggedIn());
            return userService.getCurrentUser().getUserId();
        }
        return "user not log-in";
    }
    
}
