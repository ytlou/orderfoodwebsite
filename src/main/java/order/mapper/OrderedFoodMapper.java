package order.mapper;

import order.pojo.OrderedFood;
import order.vo.OrderedFoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderedFoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderedFood record);

    int insertSelective(OrderedFood record);

    OrderedFood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderedFood record);

    int updateByPrimaryKey(OrderedFood record);

    @Select("select o.id id, o.food food, o.order `order`, o.quantity quantity, f.name name, f.price price, (f.price * o.quantity) total_price " +
            "from ordered_food o join food f on o.food = f.id " +
            "where o.order = #{order}")
    List<OrderedFoodVo> selectListVoByOrderId(@Param("order") int order);
}