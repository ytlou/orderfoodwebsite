package order.mapper;

import order.pojo.Review;
import order.vo.ReviewVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(Integer id);

    List<Review> selectAll();

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);

    List<Review> selectList(Review body);

    List<ReviewVo> selectListVo(Review body);
}