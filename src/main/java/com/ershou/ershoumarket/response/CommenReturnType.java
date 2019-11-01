package com.ershou.ershoumarket.response;

public class CommenReturnType
{
//    表明对应请求的返回处理结果
    private String status;
    private Object data;

    public static CommenReturnType create(Object result)
    {
        return CommenReturnType.create(result,"success");
    }

    public static CommenReturnType create(Object result,String status)
    {
        CommenReturnType type=new CommenReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
