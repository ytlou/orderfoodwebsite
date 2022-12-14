package order.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.Api;
import order.mapper.FoodMapper;
import order.mapper.OrderMapper;
import order.mapper.OrderedFoodMapper;
import order.mapper.UserMapper;
import order.pojo.*;
import order.utils.SessionUtils;
import order.utils.json.SimpleJson;
import order.vo.OrderedFoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = "Order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderedFoodMapper orderedFoodMapper;

    @Autowired
    FoodMapper foodMapper;

    @GetMapping("select/{id}")
    public SimpleJson select(@PathVariable("id") String id) {
        try {
            return SimpleJson.buildNotNullData(orderMapper.selectByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectList")
    public SimpleJson selectList(@RequestBody Order body) {
        try {
            return SimpleJson.buildNotNullData(orderMapper.selectList(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("create")
    public SimpleJson create(@RequestBody Order body) {
        try {
            return SimpleJson.buildNotZeroData(orderMapper.insertSelective(body), body);
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("update")
    public SimpleJson update(@RequestBody Order body) {
        try {
            return SimpleJson.notZero(orderMapper.updateByPrimaryKeySelective(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("updateStatus/{id}/{status}")
    public SimpleJson updateStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        try {
            Order body = new Order();
            body.setId(Integer.valueOf(id));
            body.setStatus(status);
            return SimpleJson.notZero(orderMapper.updateByPrimaryKeySelective(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public SimpleJson delete(@PathVariable("id") String id) {
        try {
            return SimpleJson.notZero(orderMapper.deleteByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("cancel/{id}")
    public SimpleJson cancel(@PathVariable("id") String id) {
        return updateStatus(id, Order.CANCEL);
    }

    /**
     * Check whether the user has enough money, deduct the money and update the status to Paid
     * @param id orderId
     * @return
     */
    @PostMapping("paid/{id}")
    public SimpleJson paid(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            return SimpleJson.buildNotNullData(paidTransaction(Integer.valueOf(id), SessionUtils.getUserId(request)));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Order paidTransaction(Integer orderId, Integer userId) throws Exception {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        User user = userMapper.selectByPrimaryKey(userId);
        if (order == null || user == null) {
            throw new Exception("NULL");
        }
        if (!order.getStatus().equals(Order.UNPAID)) {
            throw new Exception("order's status not is Unpaid");
        }
        if (!order.getUser().equals(user.getId())) {
            throw new Exception("order.userId != user.id");
        }
        if (user.getPaymentInfo() < order.getTotalPrice()) {
            throw new Exception("not enough to paid");
        }
        user.setPaymentInfo(user.getPaymentInfo() - order.getTotalPrice());
        order.setStatus(Order.PAID);

        userMapper.updateByPrimaryKeySelective(user);
        orderMapper.updateByPrimaryKeySelective(order);

        return order;
    }

    @PostMapping("createDetail")
    public SimpleJson createDetail(@RequestBody OrderDetail body) {
        try {
            return SimpleJson.buildNotNullData(createDetailTransaction(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderDetail createDetailTransaction(OrderDetail body) throws Exception {
        if (body.getUser() == null) {
            throw new Exception("user = null");
        }
        if (body.getRestaurant() == null) {
            throw new Exception("restaurant = null");
        }
        if (body.getOrderedFoods() == null) {
            throw new Exception("orderedFoods = null");
        }
        // calc total_price
        double totalPrice = 0.0;
        ArrayList<OrderedFood> orderedFoods = new ArrayList<>();
        for (OrderedFood orderedFood : body.getOrderedFoods()) {
            if (orderedFood.getFood() == null) {
                throw new Exception("ordered food is not bound to food_id.");
            }
            OrderedFoodVo orderedFoodVo = new OrderedFoodVo();
            Food food = foodMapper.selectByPrimaryKey(orderedFood.getFood());
            orderedFoodVo.setFood(orderedFood.getFood());
            orderedFoodVo.setName(food.getName());
            orderedFoodVo.setQuantity(orderedFood.getQuantity() > 0 ? orderedFood.getQuantity() : 1);
            orderedFoodVo.setPrice(food.getPrice());
            orderedFoodVo.setTotalPrice(orderedFoodVo.getPrice() * orderedFoodVo.getQuantity());
            totalPrice += orderedFoodVo.getTotalPrice();
            orderedFoods.add(orderedFoodVo);
        }
        body.setOrderedFoods(orderedFoods);
        // create order
        body.setStatus(Order.UNPAID);
        body.setTotalPrice(totalPrice);
        body.setTime(new Date());
        body.setId(null);
        orderMapper.insert(body);
        // set ordered foods' orderId And insert
        for (OrderedFood orderedFood : body.getOrderedFoods()) {
            orderedFood.setOrder(body.getId());
            orderedFoodMapper.insertSelective(orderedFood);
        }
        return body;
    }

    @GetMapping("selectDetail/{id}")
    public SimpleJson selectDetail(@PathVariable("id") String id) {
        try {
            return SimpleJson.buildData(selectOrderDetail(orderMapper.selectByPrimaryKey(Integer.valueOf(id))));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectDetailList")
    public SimpleJson selectDetailList(@RequestBody Order body) {
        try {
            List<OrderDetail> res = new ArrayList<>();
            for (Order order : orderMapper.selectList(body)) {
                res.add(selectOrderDetail(order));
            }
            return SimpleJson.buildData(res);
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    private OrderDetail selectOrderDetail(Order order) {
        OrderDetail orderDetail = new OrderDetail(order);
        List<OrderedFood> orderedFoods = new ArrayList<>(orderedFoodMapper.selectListVoByOrderId(order.getId()));
        orderDetail.setOrderedFoods(orderedFoods);
        return orderDetail;
    }


}
