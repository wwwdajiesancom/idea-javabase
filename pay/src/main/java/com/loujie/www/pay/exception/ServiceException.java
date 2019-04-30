package com.loujie.www.pay.exception;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 *
 * @author loujie
 * @date 2016-09-13
 * @time 15:28
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -6537484213164507226L;

    public static enum ExceptionStatus {
        SUCCESS, FAIL
    }

    private ExceptionStatus status = ExceptionStatus.FAIL;// 默认状态是失败状态的

    public ServiceException(ExceptionStatus... exceptionStatus) {
        super();
        this.setStatus(exceptionStatus);
    }

    public ServiceException(String message, ExceptionStatus... exceptionStatus) {
        super(message);
        this.setStatus(exceptionStatus);
    }

    public ServiceException(Throwable cause, ExceptionStatus... exceptionStatus) {
        super(cause);
        this.setStatus(exceptionStatus);
    }

    public ServiceException(String message, Throwable cause, ExceptionStatus... exceptionStatus) {
        super(message, cause);
        this.setStatus(exceptionStatus);
    }

    private void setStatus(ExceptionStatus... exceptionStatus) {
        if (exceptionStatus != null && exceptionStatus.length == 1) {
            this.status = exceptionStatus[0];
        }
    }

    /**
     * 判断是否为成功异常
     *
     * @return
     */
    public boolean isSuccess() {
        if (this.status == ExceptionStatus.SUCCESS) {
            return true;
        }
        return false;
    }

}
