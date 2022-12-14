package order.utils.sql;


import static com.sun.xml.internal.fastinfoset.stax.events.Util.isEmptyString;

public class SqlUtil {
    /**
     * @param s 需要修改的字符串
     * @return 如果s为""或者null 则返回 null,否则返回%s%
     */
    public static String like(String s) {
        if (isEmptyString(s)) {
            return null;
        }
        return "%" + s + "%";
    }

    public static String likeLeft(String s) {
        if (isEmptyString(s)) {
            return null;
        }
        return s + "%";
    }

    public static String likeRight(String s) {
        if (isEmptyString(s)) {
            return null;
        }
        return "%" + s;
    }

    /**
     * 用于避免前端传入的错误数字0
     * 如果数据可以含0就不要用这个了
     *
     * @param i 前端传入数字
     * @return null|0 -> null
     */
    public static Integer number(Integer i) {
        return (i == null || i == 0) ? null : i;
    }


}
