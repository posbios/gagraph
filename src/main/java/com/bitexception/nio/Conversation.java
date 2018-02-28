package com.bitexception.nio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 *
 *
 */
public abstract class Conversation {

    public static Properties properties;

    static {
        properties = new Properties();
    }

    protected final URI uri;

    /**
     *
     * @param url
     * @param requestMethod
     * @throws IOException
     */
    public Conversation(URI uri) throws IOException {
        this.uri = uri;
    }

    /**
     *
     * @param message
     * @return @throws IOException
     * @throws URISyntaxException
     */
    public abstract String conversation(String message) throws IOException, URISyntaxException;
}
