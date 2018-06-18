package la.renzhen.basis.asserts;


import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import lombok.Getter;

/**
 * 系统拦截的异常处理
 */
@Getter
public class HandlerException extends RuntimeException {

    int status;
    String error;
    String message;

    public HandlerException() {
        this(Errors.InternalSystemError);
    }

    public HandlerException(Errors errorEnum) {
        this(errorEnum.status, errorEnum.code, errorEnum.message);
    }

    public HandlerException(Errors errorEnum, String message) {
        this(errorEnum.status, errorEnum.code, message);
    }

    public HandlerException(Errors errorEnum, Throwable throwable) {
        this(errorEnum.status, errorEnum.code, com.google.common.base.Throwables.getStackTraceAsString(throwable));
    }

    public HandlerException(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = Optional.fromNullable(message).or("服务器忙，请稍后重试！");
    }

    public HandlerException(int status, String error, Throwable throwable) {
        this(status, error, Throwables.getRootCause(throwable).getMessage());
    }

    @Override
    public String toString() {
        return String.format("status: %d, error: %s , message: %s", status, error, message);
    }
}
