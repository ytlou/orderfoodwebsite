package order.controller;

import io.swagger.annotations.Api;
import order.mapper.FoodMapper;
import order.mapper.RestaurantMapper;
import order.pojo.Food;
import order.pojo.Restaurant;
import order.utils.json.SimpleJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@Api(tags = "Restaurant")
public class RestaurantController {

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    FoodMapper foodMapper;

    @GetMapping("select/{id}")
    public SimpleJson select(@PathVariable("id") String id) {
        try {
            return SimpleJson.buildNotNullData(restaurantMapper.selectByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectList")
    public SimpleJson selectList(@RequestBody Restaurant body) {
        try {
            return SimpleJson.buildNotNullData(restaurantMapper.selectList(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("create")
    public SimpleJson create(@RequestBody Restaurant body) {
        try {
            return SimpleJson.buildNotZeroData(restaurantMapper.insertSelective(body), body);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("update")
    public SimpleJson update(@RequestBody Restaurant body) {
        try {
            return SimpleJson.notZero(restaurantMapper.updateByPrimaryKeySelective(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }


    public SimpleJson delete() {
        try {
            return null;
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @GetMapping("menus/{id}")
    public SimpleJson menus(@PathVariable("id") String id) {
        try {
            Food food = new Food();
            food.setRestaurant(Integer.valueOf(id));
            return SimpleJson.buildNotNullData(foodMapper.selectList(food));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }
}
