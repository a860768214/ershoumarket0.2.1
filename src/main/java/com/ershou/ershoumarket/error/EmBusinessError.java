package com.ershou.ershoumarket.error;

public enum EmBusinessError implements CommonError
{
//    00001为通用错误类型
    PARAMETER_VALIDATION_ERROR(00001,"参数不存在"),
//    10000开头为用户错误码
    USER_NOT_EXIST(10001,"用户不存在")
    ;
    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg)
    {
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    @Override
    public int getErrCode()
    {
        return errCode;
    }

    @Override
    public String getErrMsg()
    {
        return errMsg;
    }

    @Override
    public CommonError setErrmsg(String ErrMsg)
    {
        this.errMsg=errMsg;
        return this;
    }
}
