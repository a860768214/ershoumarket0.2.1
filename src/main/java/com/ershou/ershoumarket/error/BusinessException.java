package com.ershou.ershoumarket.error;

public class BusinessException extends Exception implements CommonError
{
    private CommonError commonError;

    public BusinessException(CommonError commonError)
    {
        super();
        this.commonError = commonError;
    }

    public BusinessException(String message, CommonError commonError)
    {
        super();
        this.commonError = commonError;
        this.commonError.setErrmsg(message);
    }

    @Override
    public int getErrCode()
    {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg()
    {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrmsg(String ErrMsg)
    {
        this.commonError.setErrmsg(ErrMsg);
        return this;
    }
}
