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
@RequestMapping("/food")
@Api(tags = "Food")
public class FoodController {

    @Autowired
    FoodMapper foodMapper;

    @GetMapping("select/{id}")
    public SimpleJson select(@PathVariable("id") String id) {
        try {
            return SimpleJson.buildNotNullData(foodMapper.selectByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectList")
    public SimpleJson selectList(@RequestBody Food body) {
        try {
            return SimpleJson.buildNotNullData(foodMapper.selectList(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("create")
    public SimpleJson create(@RequestBody Food body) {
        try {
            return SimpleJson.buildNotZeroData(foodMapper.insertSelective(body), body);
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("update")
    public SimpleJson update(@RequestBody Food body) {
        try {
            return SimpleJson.notZero(foodMapper.updateByPrimaryKeySelective(body));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public SimpleJson delete(@PathVariable("id") String id) {
        try {
            return SimpleJson.notZero(foodMapper.deleteByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleJson.buildFailData(e.getMessage());
        }
    }
}
