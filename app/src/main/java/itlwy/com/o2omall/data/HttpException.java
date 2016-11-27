package itlwy.com.o2omall.data;

/**
 * Created by mac on 16/11/19.
 */

public class HttpException extends RuntimeException {

    public static final int NO_DATA = 2;
    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public HttpException(int resultCode) {
        this(getApiExceptionMessage(resultCode), resultCode);
    }

    public HttpException(String detailMessage, int resultCode) {
        super(detailMessage);
        this.resultCode = resultCode;
    }

    /**
     * 转换错误数据
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;

        }
        return message;
    }
}
