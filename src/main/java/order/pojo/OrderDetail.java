package order.pojo;

import java.io.Serializable;
import java.util.List;

public class OrderDetail extends Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<? extends OrderedFood> orderedFoods;

    public OrderDetail(Order order) {
        this.setId(order.getId());
        this.setTime(order.getTime());
        this.setStatus(order.getStatus());
        this.setTotalPrice(order.getTotalPrice());
        this.setRestaurant(order.getRestaurant());
        this.setUser(order.getUser());
    }

    public OrderDetail() {
    }

    public List<? extends OrderedFood> getOrderedFoods() {
        return orderedFoods;
    }

    public void setOrderedFoods(List<OrderedFood> orderedFoods) {
        this.orderedFoods = orderedFoods;
    }
}
