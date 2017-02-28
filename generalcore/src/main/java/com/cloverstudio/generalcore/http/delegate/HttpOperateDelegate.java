package com.cloverstudio.generalcore.http.delegate;

public interface HttpOperateDelegate<T> {

    /**
     * 任务顺利只执行完成
     *
     * @param object 任务执行完成后的返回对象
     */
    void executeComplete(T object);

    /**
     * 任务顺利执行完成但是出现了错误
     *
     * @param error 错误信息
     */
    void executeCompltetButHashError(String error);

    /**
     * 任务执行出错， 系统会自动弹出错误提示， 此处为如果需要做提示意外的操作，便把任务写在此处
     */
    void executeError();
}
