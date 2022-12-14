package order.utils;

import order.mapper.UserMapper;
import order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static order.utils.constant.SessionConstant.USER;

public class SessionUtils {

    public static User getUser(HttpServletRequest request) {
        return getUser(request.getSession());
    }

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(USER);
    }

    public static Integer getUserId(HttpServletRequest request) {
        return getUser(request).getId();
    }
}
