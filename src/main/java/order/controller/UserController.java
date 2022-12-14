package order.controller;



import io.swagger.annotations.Api;

import order.mapper.UserMapper;
import order.pojo.User;
import order.utils.SessionUtils;
import order.utils.constant.SessionConstant;
import order.utils.json.SimpleJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/user")
@Api(tags = "User")
public class UserController {


    @Autowired
    UserMapper userMapper;


    @PostMapping("login/{email}/{password}")
    public SimpleJson login(@PathVariable("email") String email,
                            @PathVariable("password") String password,
                            HttpServletRequest request,
                            HttpServletResponse response)  {
        User user = null;
        try {
            user = userMapper.selectByEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                return SimpleJson.FAILURE;
            }

            HttpSession session = request.getSession();
            String sessionId = session.getId();
            System.err.println(session);
            System.out.println("sessionId:" + sessionId);
            session.setAttribute(SessionConstant.USER, user);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }


        return SimpleJson.buildData(user);
    }

    @PostMapping("register")
    public SimpleJson register(@RequestBody User registerUser) {
        try {
            int cnt = userMapper.insertSelective(registerUser);
            if (cnt == 0) {
                return null;
            }
            User user = userMapper.selectByEmail(registerUser.getEmail());
            return user == null ? SimpleJson.FAILURE : SimpleJson.buildData(user);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }


    @RequestMapping("selectList")
    public SimpleJson selectList(@RequestBody User body) {
        try {
            return SimpleJson.buildData(userMapper.selectList(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @GetMapping("selectById/{id}")
    public SimpleJson selectById(@PathVariable String id) {
        try {
            User user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
            return user == null ?
                    SimpleJson.FAILURE : SimpleJson.buildData(user);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @GetMapping("loginUser")
    public SimpleJson loginUser(HttpServletRequest request) {
        try {
            User sessionUser = SessionUtils.getUser(request);
            User user = userMapper.selectByPrimaryKey(sessionUser.getId());

            if (user == null) {
                request.getSession().removeAttribute(SessionConstant.USER);
            } else {
                request.getSession().setAttribute(SessionConstant.USER, user);
            }

            return SimpleJson.buildData(user);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @GetMapping("selectByEmail/{email}")
    public SimpleJson selectByEmail(@PathVariable String email) {
        try {
            User user = userMapper.selectByEmail(email);
            return user == null ?
                    SimpleJson.FAILURE : SimpleJson.buildData(user);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("update")
    public SimpleJson update(@RequestBody User user) {
        try {
            int cnt = userMapper.updateByPrimaryKeySelective(user);
            return cnt == 0 ?
                    SimpleJson.FAILURE :
                    SimpleJson.buildData(userMapper.selectByPrimaryKey(user.getId()));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public SimpleJson delete(@PathVariable String id) {
        try {
            int cnt = userMapper.deleteByPrimaryKey(Integer.valueOf(id));
            return cnt == 0 ?
                    SimpleJson.FAILURE : SimpleJson.SUCCESS;
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }
}
