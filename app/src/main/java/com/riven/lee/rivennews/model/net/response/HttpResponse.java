package com.riven.lee.rivennews.model.net.response;

/**
 * @author rivenlee
 * @date 2017/10/11 19:35
 * 统一规范处理返回的response
 */

public class HttpResponse<T> {

    private boolean error;

    private T results;

    public boolean getError() {
        return error;
    }

    public T getResults() {
        return results;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
