package la.renzhen.basis.web;

import lombok.Data;

/**
 * 统一业务返回内容<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 31/05/2018 7:25 PM
 */
@Data
public class Result<T> {
    public static final int OK = 200;
    public static final int NET_ERROR = 500;
    public static final int NOTFOUND_ERROR = 404;
    public static final int HYSTRIX_ERROR = 502;
    public static final int DECODER_ERROR = 501;

    int code = NET_ERROR;

    String message;

    T data;

    public boolean isOk() {
        return code == OK;
    }

    public boolean is(int code) {
        return this.code == code;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.code = OK;
        result.data = data;
        return result;
    }

    public static Result error(int code, String message) {
        Result result = new Result();
        result.code = code;
        result.message = message;
        return result;
    }

    public static Result hystrix(String message) {
        return error(HYSTRIX_ERROR, message);
    }

    public static Result notFound(String message) {
        return error(NOTFOUND_ERROR, message);
    }

    public static Result decoderError(String message) {
        return error(DECODER_ERROR, message);
    }
}