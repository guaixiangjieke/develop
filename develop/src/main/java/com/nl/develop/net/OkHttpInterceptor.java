/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nl.develop.net;


import android.support.v4.util.ArrayMap;

import com.google.gson.JsonObject;
import com.nl.develop.utils.LogTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * okhttp请求拦截器 手机网络请求数据
 */
public final class OkHttpInterceptor implements Interceptor {
    public static final int MAX = 5;
    public static final String NEW_LINE = System.getProperty("line.separator");
    private static final LinkedBlockingQueue<ArrayMap<String, String>> requestQueue = new LinkedBlockingQueue<>();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static LinkedBlockingQueue<ArrayMap<String, String>> getRequestQueue() {
        return requestQueue;
    }

    class StringBuilderProxy implements Appendable {
        private final StringBuilder stringBuilder = new StringBuilder();

        @Override
        public Appendable append(CharSequence csq) throws IOException {
            stringBuilder.append(csq);
            stringBuilder.append(NEW_LINE);
            return stringBuilder;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            stringBuilder.append(csq, start, end);
            stringBuilder.append(NEW_LINE);
            return stringBuilder;
        }

        @Override
        public Appendable append(char c) throws IOException {
            stringBuilder.append(c);
            stringBuilder.append(NEW_LINE);
            return stringBuilder;
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuilderProxy stringBuilder = new StringBuilderProxy();

        Request request = chain.request();
        boolean logBody = true;
        boolean logHeaders = true;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        stringBuilder.append(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    stringBuilder.append("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    stringBuilder.append("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    stringBuilder.append(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                stringBuilder.append("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                stringBuilder.append("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                stringBuilder.append("");
                if (isPlaintext(buffer)) {
                    stringBuilder.append(buffer.readString(charset));
                    stringBuilder.append("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    stringBuilder.append("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            stringBuilder.append("<-- HTTP FAILED: " + e);
            put(request.url().toString(), stringBuilder.toString());
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        stringBuilder.append("<-- "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + " (" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                stringBuilder.append(headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                stringBuilder.append("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                stringBuilder.append("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    stringBuilder.append("");
                    stringBuilder.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    put(request.url().toString(), stringBuilder.toString());
                    return response;
                }

                if (contentLength != 0) {
                    stringBuilder.append("");
                    String responseStr = buffer.clone().readString(charset);
                    stringBuilder.append(convert(responseStr));
                    stringBuilder.append("");

                }

                stringBuilder.append("<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }
        put(request.url().toString(), stringBuilder.toString());
        return response;
    }

    String convert(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    void put(String key, String value) {
        while (requestQueue.size() >= MAX) {
            requestQueue.poll();
        }
        ArrayMap<String, String> params = new ArrayMap<>();
        params.put(key, value);
        requestQueue.offer(params);
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
}
