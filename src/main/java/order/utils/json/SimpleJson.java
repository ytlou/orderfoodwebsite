package order.utils.json;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleJson {
    private int code = 0;
    private String msg = "";
    private Object data;

    public static final SimpleJson SUCCESS = new SimpleJson(0,"SUCCESS");
    public static final SimpleJson FAILURE = new SimpleJson(1,"FAILURE");
    public static final SimpleJson NULL_FAILURE = new SimpleJson(2,"NULL");
    public static final SimpleJson AUTHORITY_FAILURE = new SimpleJson(3,"NO AUTHORITY");
    public static final SimpleJson DO_NOTHING = new SimpleJson(4,"DO NO THING");
    public static final SimpleJson NOT_LOGIN_FAILURE = new SimpleJson(5,"NOT LOGIN");

    public SimpleJson(String msg){
        this.code = 1;
        this.msg = msg;
    }

    public SimpleJson(int code){
        this.code = code;
    }

    public SimpleJson(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static SimpleJson  buildData(Object o){
        return new SimpleJson(0,"SUCCESS",o);
    }

    public static SimpleJson  buildFailData(Object o){
        return new SimpleJson(1,"FAILURE",o);
    }

    public static SimpleJson buildNotNullData(Object o){
        if (isNull(o)) {
            return SimpleJson.NULL_FAILURE;
        }
        return SimpleJson.buildData(o);
    }

    public static SimpleJson buildNotZeroData(int cnt, Object o) {
        return cnt == 0 ? FAILURE : buildData(o);
    }

    public static SimpleJson buildBoolJsonData(boolean flag, Object o) {
        return flag ? FAILURE : buildData(o);
    }

    public static SimpleJson notZero(int cnt) {
        return cnt == 0 ? FAILURE : SUCCESS;
    }

    public static SimpleJson boolJson(boolean flag) {
        return flag ? FAILURE : SUCCESS;
    }
}
