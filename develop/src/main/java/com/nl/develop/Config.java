package com.nl.develop;

import com.nl.develop.json.JsonFactory;
import com.nl.develop.json.JsonImpGson;
import com.nl.develop.net.NetFactory;
import com.nl.develop.net.NetImpOkHttp;

/**
 * Created by NiuLei on 2018/2/23.
 * 配置
 */

public class Config {
    final NetFactory netFactory;
    final JsonFactory jsonFactory;

    Config(Builder builder) {
        netFactory = builder.netFactory;
        jsonFactory = builder.jsonFactory;
    }

    public NetFactory netFactory() {
        return netFactory;
    }

    public JsonFactory jsonFactory() {
        return jsonFactory;
    }

    public static final class Builder {
        NetFactory netFactory = new NetImpOkHttp();
        //        NetFactory netFactory = new NetImpNative();
        JsonFactory jsonFactory = new JsonImpGson();

        public Config build() {
            return new Config(this);
        }

        Builder netFactory(NetFactory netFactory) {
            this.netFactory = netFactory;
            return this;
        }

        Builder jsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }
    }
}
