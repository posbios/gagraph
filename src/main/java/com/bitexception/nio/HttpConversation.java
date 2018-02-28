package com.bitexception.nio;

import static com.bitexception.nio.Conversation.properties;
import com.bitexception.rest.command.UriCommand;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an configurable RequestMethod http conversation with or without
 * parameters
 *
 *
 */
public class HttpConversation extends Conversation {

    public HttpConversation setRequestProperty(RequestProperty requestProperty) {
        this.requestProperties.add(requestProperty);
        return this;
    }

    public enum ContentType {

        JSON("application/json"), XML("application/xml");
        private final String contentType;

        private ContentType(String contentType) {
            this.contentType = contentType;
        }

        public String get() {
            return contentType;
        }
    }

    public enum RequestMethod {

        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
        PATCH
    }

    public static final String USER_AGENT_PROPERTY = "user_agent";
    public static final String USER_PROPERTY = "user";
    public static final String PASS_PROPERTY = "pass";
    public static final String CONNECTION_TIMEOUT_PROPERTY = "connection_timeout";

    private RequestMethod requestMethod;
    private ContentType contentType;
    private final List<RequestProperty> requestProperties;

    /**
     * @param requestMethod the requestMethod to set
     * @return
     */
    public HttpConversation setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    /**
     * @param contentType
     * @return
     */
    public HttpConversation setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     *
     * @return
     */
    protected String getBasicAuthenticationEncoding() {
        return "Basic " + new String(Base64.getEncoder().encode(MessageFormat.format("{0}:{1}", properties.getProperty(USER_PROPERTY), properties.getProperty(PASS_PROPERTY)).getBytes()));
    }

    public HttpConversation(URI uri) throws IOException, URISyntaxException {
        super(uri);
        this.requestMethod = RequestMethod.GET;
        this.contentType = ContentType.JSON;
        this.requestProperties = new ArrayList<>();
    }

    /**
     *
     * @param url
     * @param requestMethod
     * @throws IOException
     */
    public HttpConversation(URL url) throws IOException, URISyntaxException {
        this(url.toURI());
    }

    /**
     *
     * @param command
     * @param requestMethod
     * @throws IOException
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    public HttpConversation(UriCommand command) throws IOException, MalformedURLException, URISyntaxException {
        this(command.uri());
    }

    public String conversation() throws IOException, URISyntaxException {
        return conversation(null);
    }

    @Override
    public String conversation(String message) throws IOException, URISyntaxException {
        String result;
        HttpURLConnection connection;
        BufferedInputStream is;
        DataOutputStream requestOutputStream;
        ByteArrayOutputStream responseOutputStream;
        byte[] buffer;

        // prepare data
        is = null;
        requestOutputStream = null;
        responseOutputStream = null;
        try {
            connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(Integer.valueOf(properties.getProperty(CONNECTION_TIMEOUT_PROPERTY, "3000")));
            connection.setRequestMethod(requestMethod.name());
            connection.setRequestProperty("User-Agent", properties.getProperty(USER_AGENT_PROPERTY));
            connection.setRequestProperty("Authorization", getBasicAuthenticationEncoding());

            for (RequestProperty rp : requestProperties) {
                connection.setRequestProperty(rp.id, rp.value);
            }

            if (null != message) {
                connection.setRequestProperty("Content-Length", String.valueOf(message.length()));
                connection.setRequestProperty("Content-Type", contentType.get());

                // send data
                requestOutputStream = new DataOutputStream(connection.getOutputStream());
                requestOutputStream.write(message.getBytes());
                requestOutputStream.flush();
            }

            // get response
            is = new BufferedInputStream(connection.getInputStream(), 64);
            responseOutputStream = new ByteArrayOutputStream();

            buffer = new byte[16];

            int nRead;
            while (-1 != (nRead = is.read(buffer, 0, buffer.length))) {
                responseOutputStream.write(buffer, 0, nRead);
            }

            result = new String(responseOutputStream.toByteArray());
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpConversation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (null != responseOutputStream) {
                try {
                    responseOutputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpConversation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (null != requestOutputStream) {
                try {
                    requestOutputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpConversation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    public static class RequestProperty {

        private final String id;
        private final String value;

        public RequestProperty(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }
}
