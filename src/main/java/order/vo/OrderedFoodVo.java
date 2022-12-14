package order.vo;

import lombok.Data;
import order.pojo.Food;
import order.pojo.OrderedFood;

import java.io.Serializable;
import java.util.Objects;

@Data
public class OrderedFoodVo extends OrderedFood implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Double price;
    private Double totalPrice;

}
