package calculator.demo.mainmodule.di;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okio.Buffer;

import static okhttp3.internal.platform.Platform.INFO;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.internal.platform.Platform;

public final class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        TokenInterceptor.Logger DEFAULT = new TokenInterceptor.Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public TokenInterceptor() {
        this(Logger.DEFAULT);
    }

    public TokenInterceptor(Logger logger) {
        this.logger = logger;
    }

    public TokenInterceptor(TokenManger tokenManger,
                            Gson gson) {
        this(Logger.DEFAULT);
        this.tokenManger = tokenManger;
        this.gson = gson;
    }

    private final Logger logger;
    private TokenManger tokenManger = null;
    private Gson gson = null;

    private Context globalContext = null;

    private volatile Level level = Level.NONE;
    private volatile TokenManger.State state = TokenManger.State.STATE_NEW;

    /**
     * Change the level at which this interceptor logs.
     */
    public TokenInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    private boolean isSessionExpired(String errorCode) {
        return (errorCode.equals("BFSD-403") || errorCode.equals("BFSD-401"));
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

      /*  if (!InternetUtils.INSTANCE.isInternetOn())  if(((MarketsApplication)tokenManger.getContext()).getMInternetConnectionListener() != null){
            ((MarketsApplication)tokenManger.getContext()).getMInternetConnectionListener().onInternetUnavailable();
        }*/
        Response response = null;

        return response;
    }




    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }


    private String getUserToken() {
        if (!TextUtils.isEmpty(tokenManger.getUserToken())) {
            return tokenManger.getUserToken();
        }
        return "";
    }

    private String getAuthToken() {
        if (!TextUtils.isEmpty(tokenManger.getAuthToken())) {
            return tokenManger.getAuthToken();
        }
        return "";
    }

}

