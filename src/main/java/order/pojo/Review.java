package order.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import order.utils.constant.DateConstant;
import org.thymeleaf.util.DateUtils;

/**
 * review
 * @author 
 */
@Data
public class Review implements Serializable {
    private Integer id;

    /**
     * The score ranges from 1 to 10
     */
    private Integer rating;

    private String comments;

    private Integer restaurant;

    @JsonFormat(pattern = DateConstant.DATETIME_FORMAT)
    private Date time;

    /**
     * If the user id is empty, it is an anonymous comment
     */
    private Integer user;


    private static final long serialVersionUID = 1L;

    public void setRating(Integer rating) {
        if (rating != null) {
            rating = (rating < 1 ? 1 : (rating > 10 ? 10 : rating));
        }
        this.rating = rating;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Review other = (Review) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getComments() == null ? other.getComments() == null : this.getComments().equals(other.getComments()))
            && (this.getRestaurant() == null ? other.getRestaurant() == null : this.getRestaurant().equals(other.getRestaurant()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getUser() == null ? other.getUser() == null : this.getUser().equals(other.getUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getComments() == null) ? 0 : getComments().hashCode());
        result = prime * result + ((getRestaurant() == null) ? 0 : getRestaurant().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rating=").append(rating);
        sb.append(", comments=").append(comments);
        sb.append(", restaurant=").append(restaurant);
        sb.append(", time=").append(time);
        sb.append(", user=").append(user);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}