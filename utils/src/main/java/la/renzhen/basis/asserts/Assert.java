package la.renzhen.basis.asserts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 提供一些公用的检查方法<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/7/10 下午12:09
 */
@Slf4j
public class Assert {

    @AllArgsConstructor
    @Getter
    public static class Error {
        String Code;
        String Message;
    }

    public static void assertBadRequest(String logs, boolean check, String message) {
        assertBadRequest(logs, check, Errors.BadRequest.code, message);
    }

    public static void assertBadRequest(String logs, boolean check, Assert.Error error) {
        assertBadRequest(logs, check, error.Code, error.Message);
    }

    public static void assertBadRequest(boolean check, String message) {
        assertBadRequest(null, check, Errors.BadRequest.code, message);
    }

    public static void assertBadRequest(boolean check, String code, String message) {
        assertBadRequest(null, check, code, message);
    }

    public static void assertBadRequest(boolean check, Assert.Error error) {
        assertBadRequest(null, check, error.Code, error.Message);
    }

    public static void assertBadRequest(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.BadRequest, code, message);
            }
            throw new AssertException(Errors.BadRequest.status, code, message);
        }
    }

    public static void assertUnauthorized(String logs, boolean check, String message) {
        assertUnauthorized(logs, check, Errors.Unauthorized.code, message);
    }

    public static void assertUnauthorized(String logs, boolean check, Assert.Error error) {
        assertUnauthorized(logs, check, error.Code, error.Message);
    }

    public static void assertUnauthorized(boolean check, String message) {
        assertUnauthorized(null, check, Errors.Unauthorized.code, message);
    }

    public static void assertUnauthorized(boolean check, String code, String message) {
        assertUnauthorized(null, check, code, message);
    }

    public static void assertUnauthorized(boolean check, Assert.Error error) {
        assertUnauthorized(null, check, error.Code, error.Message);
    }

    public static void assertUnauthorized(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.Unauthorized, code, message);
            }
            throw new AssertException(Errors.Unauthorized.status, code, message);
        }
    }

    public static void assertForbidden(String logs, boolean check, String message) {
        assertForbidden(logs, check, Errors.Forbidden.code, message);
    }

    public static void assertForbidden(String logs, boolean check, Assert.Error error) {
        assertForbidden(logs, check, error.Code, error.Message);
    }

    public static void assertForbidden(boolean check, String message) {
        assertForbidden(null, check, Errors.Forbidden.code, message);
    }

    public static void assertForbidden(boolean check, String code, String message) {
        assertForbidden(null, check, code, message);
    }

    public static void assertForbidden(boolean check, Assert.Error error) {
        assertForbidden(null, check, error.Code, error.Message);
    }

    public static void assertForbidden(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.Forbidden, code, message);
            }
            throw new AssertException(Errors.Forbidden.status, code, message);
        }
    }

    public static void assertNotFound(String logs, boolean check, String message) {
        assertNotFound(logs, check, Errors.NotFound.code, message);
    }

    public static void assertNotFound(String logs, boolean check, Assert.Error error) {
        assertNotFound(logs, check, error.Code, error.Message);
    }

    public static void assertNotFound(boolean check, String message) {
        assertNotFound(null, check, Errors.NotFound.code, message);
    }

    public static void assertNotFound(boolean check, String code, String message) {
        assertNotFound(null, check, code, message);
    }

    public static void assertNotFound(boolean check, Assert.Error error) {
        assertNotFound(null, check, error.Code, error.Message);
    }

    public static void assertNotFound(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.NotFound, code, message);
            }
            throw new AssertException(Errors.NotFound.status, code, message);
        }
    }

    public static void assertInvalidArgument(String logs, boolean check, String message) {
        assertInvalidArgument(logs, check, Errors.InvalidArgument.code, message);
    }

    public static void assertInvalidArgument(String logs, boolean check, Assert.Error error) {
        assertInvalidArgument(logs, check, error.Code, error.Message);
    }

    public static void assertInvalidArgument(boolean check, String message) {
        assertInvalidArgument(null, check, Errors.InvalidArgument.code, message);
    }

    public static void assertInvalidArgument(boolean check, String code, String message) {
        assertInvalidArgument(null, check, code, message);
    }

    public static void assertInvalidArgument(boolean check, Assert.Error error) {
        assertInvalidArgument(null, check, error.Code, error.Message);
    }

    public static void assertInvalidArgument(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.InvalidArgument, code, message);
            }
            throw new AssertException(Errors.InvalidArgument.status, code, message);
        }
    }

    public static void assertMethodNotAllowed(String logs, boolean check, String message) {
        assertMethodNotAllowed(logs, check, Errors.MethodNotAllowed.code, message);
    }

    public static void assertMethodNotAllowed(String logs, boolean check, Assert.Error error) {
        assertMethodNotAllowed(logs, check, error.Code, error.Message);
    }

    public static void assertMethodNotAllowed(boolean check, String message) {
        assertMethodNotAllowed(null, check, Errors.MethodNotAllowed.code, message);
    }

    public static void assertMethodNotAllowed(boolean check, String code, String message) {
        assertMethodNotAllowed(null, check, code, message);
    }

    public static void assertMethodNotAllowed(boolean check, Assert.Error error) {
        assertMethodNotAllowed(null, check, error.Code, error.Message);
    }

    public static void assertMethodNotAllowed(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.MethodNotAllowed, code, message);
            }
            throw new AssertException(Errors.MethodNotAllowed.status, code, message);
        }
    }

    public static void assertMissingParameter(String logs, boolean check, String message) {
        assertMissingParameter(logs, check, Errors.MissingParameter.code, message);
    }

    public static void assertMissingParameter(String logs, boolean check, Assert.Error error) {
        assertMissingParameter(logs, check, error.Code, error.Message);
    }

    public static void assertMissingParameter(boolean check, String message) {
        assertMissingParameter(null, check, Errors.MissingParameter.code, message);
    }

    public static void assertMissingParameter(boolean check, String code, String message) {
        assertMissingParameter(null, check, code, message);
    }

    public static void assertMissingParameter(boolean check, Assert.Error error) {
        assertMissingParameter(null, check, error.Code, error.Message);
    }

    public static void assertMissingParameter(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.MissingParameter, code, message);
            }
            throw new AssertException(Errors.MissingParameter.status, code, message);
        }
    }

    public static void assertExecuteFailed(String logs, boolean check, String message) {
        assertExecuteFailed(logs, check, Errors.ExecuteFailed.code, message);
    }

    public static void assertExecuteFailed(String logs, boolean check, Assert.Error error) {
        assertExecuteFailed(logs, check, error.Code, error.Message);
    }

    public static void assertExecuteFailed(boolean check, String message) {
        assertExecuteFailed(null, check, Errors.ExecuteFailed.code, message);
    }

    public static void assertExecuteFailed(boolean check, String code, String message) {
        assertExecuteFailed(null, check, code, message);
    }

    public static void assertExecuteFailed(boolean check, Assert.Error error) {
        assertExecuteFailed(null, check, error.Code, error.Message);
    }

    public static void assertExecuteFailed(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.ExecuteFailed, code, message);
            }
            throw new AssertException(Errors.ExecuteFailed.status, code, message);
        }
    }

    public static void assertInternalSystemError(String logs, boolean check, String message) {
        assertInternalSystemError(logs, check, Errors.InternalSystemError.code, message);
    }

    public static void assertInternalSystemError(String logs, boolean check, Assert.Error error) {
        assertInternalSystemError(logs, check, error.Code, error.Message);
    }

    public static void assertInternalSystemError(boolean check, String message) {
        assertInternalSystemError(null, check, Errors.InternalSystemError.code, message);
    }

    public static void assertInternalSystemError(boolean check, String code, String message) {
        assertInternalSystemError(null, check, code, message);
    }

    public static void assertInternalSystemError(boolean check, Assert.Error error) {
        assertInternalSystemError(null, check, error.Code, error.Message);
    }

    public static void assertInternalSystemError(String logs, boolean check, String code, String message) {
        if (!check) {
            if (logs != null) {
                log.info("{} error. {} {} {}", logs, Errors.InternalSystemError, code, message);
            }
            throw new AssertException(Errors.InternalSystemError.status, code, message);
        }
    }

}
