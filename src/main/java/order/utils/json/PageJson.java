package order.utils.json;

import lombok.Data;

import java.util.List;

@Data
public class PageJson {
    private int code = 0;//0为成功
    private String msg = "";//返回的提示
    private long count = 0;//总条目
    private List<?> data;//数据列表

    public static final PageJson FAILURE = new PageJson(SimpleJson.FAILURE);
    public static final PageJson SUCCESS = new PageJson(SimpleJson.SUCCESS);
    public static final PageJson AUTHORITY_FAILURE = new PageJson(2,"权限不足",0,null);
    public static final PageJson NO_LOGIN_FAILURE = new PageJson(3,"未登录",0,null);

    public PageJson(int code,String msg,long count,List<?>data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }


    public PageJson(){

    }

    public PageJson(List<?> data) {
        this(0,"",data.size(),data);
    }

    public PageJson(long count,List<?> data) {
        this(0,"",count,data);
    }

    public PageJson(SimpleJson json){
        this(json.getCode(),json.getMsg(),0,null);
    }

    public PageJson(SimpleJson json,List<?>data){
        this(json.getCode(),json.getMsg(),data.size(),data);
    }

    public PageJson(String msg,List<?>data){
        this(0,msg,data.size(),data);
    }


    public PageJson(SimpleJson json,long count,List<?>data){
        this(json.getCode(),json.getMsg(),count,data);
    }

    public PageJson(String msg,long count,List<?>data){
        this(0,msg,count,data);
    }

}
