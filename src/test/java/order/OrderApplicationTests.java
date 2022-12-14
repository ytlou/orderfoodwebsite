package order;

import order.mapper.UserMapper;
import order.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Random;

@SpringBootTest
class OrderApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    UserMapper userMapper;
    @Test
    void database() {

        User user = new User();
        user.setAddress(getRandomString(LETTER_NUMBER, 4, 10));
        user.setDob(new Date());
        user.setIcon("https://profile-avatar.csdnimg.cn/0fe072bb5f2c4750b32d77f4dfb162d0_ctrl_kun.jpg!0");
        String email = getRandomString(LETTER_NUMBER,1, 10) + "@" + getRandomString(LOWERCASE_LETTER, 3, 8);
        user.setEmail(email);
        user.setUserName(getRandomString(LETTER, 4, 15));
        user.setPassword("123");
        user.setRole(User.CUSTOMER);

        userMapper.insertSelective(user);

        user = userMapper.selectByEmail(email);
        user.setPaymentInfo(1000.0);

        userMapper.updateByPrimaryKeySelective(user);


        userMapper.selectAll();
    }

    //（A-Z，a-z，0-9）
    public static String getRandomString(String patten, int minLen, int maxLen) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int length = Math.abs(random.nextInt()) % (maxLen - minLen) + minLen;
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(patten.length());
            sb.append(patten.charAt(number));
        }
        return sb.toString();
    }

    public static String LETTER_NUMBER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String NUMBER = "0123456789";
    public static String LOWERCASE_LETTER = "abcdefghijklmnopqrstuvwxyz";
    public static String UPPERCASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
