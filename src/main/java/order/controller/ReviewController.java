package order.controller;

import io.swagger.annotations.Api;
import order.mapper.ReviewMapper;
import order.pojo.Review;
import order.utils.json.SimpleJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@Api(tags = "Review")
public class ReviewController {

    @Autowired
    ReviewMapper reviewMapper;

    @GetMapping("select/{id}")
    public SimpleJson select(@PathVariable("id") String id) {
        try {
            return SimpleJson.buildNotNullData(reviewMapper.selectByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectList")
    public SimpleJson selectList(@RequestBody Review body) {
        try {
            return SimpleJson.buildNotNullData(reviewMapper.selectList(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @RequestMapping("selectListVo")
    public SimpleJson selectListVo(@RequestBody Review body) {
        try {
            return SimpleJson.buildNotNullData(reviewMapper.selectListVo(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("create")
    public SimpleJson create(@RequestBody Review body) {
        try {
            return SimpleJson.buildNotZeroData(reviewMapper.insertSelective(body), body);
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @PostMapping("update")
    public SimpleJson update(@RequestBody Review body) {
        try {
            return SimpleJson.notZero(reviewMapper.updateByPrimaryKeySelective(body));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public SimpleJson delete(@PathVariable("id") String id) {
        try {
            return SimpleJson.notZero(reviewMapper.deleteByPrimaryKey(Integer.valueOf(id)));
        } catch (Exception e) {
            return SimpleJson.buildFailData(e.getMessage());
        }
    }
}
