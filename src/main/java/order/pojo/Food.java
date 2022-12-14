package order.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * food
 *
 * @author
 */
@Data
public class Food implements Serializable {
    private Integer id;

    private String name;

    private Double price;

    private Boolean availability;

    private String ingredients;

    /**
     * url of picture
     */
    private String picture;

    private Double calorie;

    private String type;

    private Integer restaurant;

    private static final long serialVersionUID = 1L;

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
        Food other = (Food) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getAvailability() == null ? other.getAvailability() == null : this.getAvailability().equals(other.getAvailability()))
                && (this.getIngredients() == null ? other.getIngredients() == null : this.getIngredients().equals(other.getIngredients()))
                && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()))
                && (this.getCalorie() == null ? other.getCalorie() == null : this.getCalorie().equals(other.getCalorie()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getRestaurant() == null ? other.getRestaurant() == null : this.getRestaurant().equals(other.getRestaurant()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAvailability() == null) ? 0 : getAvailability().hashCode());
        result = prime * result + ((getIngredients() == null) ? 0 : getIngredients().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        result = prime * result + ((getCalorie() == null) ? 0 : getCalorie().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRestaurant() == null) ? 0 : getRestaurant().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", availability=").append(availability);
        sb.append(", ingredients=").append(ingredients);
        sb.append(", picture=").append(picture);
        sb.append(", calorie=").append(calorie);
        sb.append(", type=").append(type);
        sb.append(", restaurant=").append(restaurant);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}