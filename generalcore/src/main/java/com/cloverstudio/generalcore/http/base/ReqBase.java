package com.cloverstudio.generalcore.http.base;

/**
 * 网络请求返回的实体中，必须包含的字段
 */
public class ReqBase {
    /**
     * 结果标示 -1不成功，1成功
     */
    private int resultFlag;
    /**
     * 结果标示为0时返回的错误信息
     */
    private String resultErrorMsg;
    /**
     * 错误码
     */
    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(int resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getResultErrorMsg() {
        return resultErrorMsg;
    }

    public void setResultErrorMsg(String resultErrorMsg) {
        this.resultErrorMsg = resultErrorMsg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("resultFlag:" + resultFlag + "\n");
        builder.append("resultErrorMsg:" + resultErrorMsg + "\n");
        return builder.toString();
    }

}
