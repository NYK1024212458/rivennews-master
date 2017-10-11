package com.riven.lee.rivennews.model.net.exception;

import java.io.IOException;

/**
 * @author rivenlee
 * @date 2017/10/11 17:36
 */

public class ApiException extends IOException {

    public ApiException(String message) {
        super(message);
    }

}
