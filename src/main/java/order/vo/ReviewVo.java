package order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import order.pojo.Review;

@Setter
@Getter
public class ReviewVo extends Review {
    private String userName;
    private String userEmail;
    private String userIcon;
}
