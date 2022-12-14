package order.utils.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlPage {
    private int id = 1;
    private int start = 0;
    private int size = 10;

    public static SqlPage normal = new SqlPage();

    public SqlPage(Integer id, Integer size) {
        if (id == null || id < 1) {
            id = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        setId(id);
        setStart((id - 1) * size);
        setSize(size);
    }
}