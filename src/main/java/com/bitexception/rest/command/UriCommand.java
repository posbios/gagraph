package com.bitexception.rest.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is an abstract class for a mix of UriBuilder, preconfigured config
 * parameters and convenience methods to execute calls
 *
 */
public abstract class UriCommand {

    /**
     *
     */
    public static Properties properties;

    static {
        properties = new Properties();
    }

    /**
     *
     */
    private final String HOST_PROTOCOL_PROPERTY = "host_protocol";
    private final String HOST_IP_PROPERTY = "host_ip";
    private final String HOST_PORT_PROPERTY = "host_port";
    private final String HOST = "{0}://{1}:{2,number,#####}/";
    private final String[] arguments;
    private String pattern;

    /**
     *
     * @param pattern
     * @param arguments
     */
    public UriCommand(String pattern, String... arguments) {
        this.pattern = pattern;
        this.arguments = arguments;
    }

    /**
     *
     * @return @throws MalformedURLException
     * @throws IOException
     * @throws URISyntaxException
     */
    public URL url() throws MalformedURLException, IOException, URISyntaxException {
        return uri().toURL();
    }

    public URI uri() throws MalformedURLException, IOException, URISyntaxException {
        return new URI(
                MessageFormat.format(HOST,
                        properties.getProperty(HOST_PROTOCOL_PROPERTY, "http"),
                        properties.getProperty(HOST_IP_PROPERTY, "localhost"),
                        Integer.valueOf(properties.getProperty(HOST_PORT_PROPERTY, "80"))))
                .resolve(new URI(
                                MessageFormat.format(pattern, arguments)));
    }

    /**
     * Overide this method just to set java documentation but ever call parent
     *
     * @param parameters
     * @param encode
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public UriCommand parameters(String parameters, Charset encode) throws UnsupportedEncodingException {
        pattern = pattern.concat("?").concat(encodeParams(parameters, encode));

        return this;
    }

    public UriCommand parameters(String parameters) throws UnsupportedEncodingException {
        return parameters(parameters, Charset.forName("UTF-8"));
    }

    /**
     * Will encode all url special characters from each query param
     * 
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException 
     */
    private String encodeParams(String params, Charset encode) throws UnsupportedEncodingException {
        StringBuffer parameterBuffers;

        Pattern p;
        Matcher m;

        p = Pattern.compile("(?<=.*=).*?(?=&|$)");
        m = p.matcher(params);
        parameterBuffers = new StringBuffer();

        while (m.find()) {
            m.appendReplacement(parameterBuffers, URLEncoder.encode(m.group(), encode.name()));
        }

        return parameterBuffers.toString();
    }

    /**
     * Will make the conversaction without params.
     *
     * This is the same to call conversation(null);
     *
     * @return A plain string with the response result
     * @throws IOException This means are there problems on comunication.
     * @throws URISyntaxException This means the url is not constructed ok.
     */
    public String conversation() throws IOException, URISyntaxException {
        return conversation(null);
    }

    /**
     * Override for a concrete comunication system.
     *
     * This method must implemente all comunication system with an uri and
     * return response as plain text.
     *
     * @see HttpConversation
     * @param data thouse are the sended params on active comunication
     * @return A plain string with the response result
     * @throws IOException This means are there problems on comunication.
     * @throws URISyntaxException This means the url is not constructed ok.
     */
    public abstract String conversation(String data) throws IOException, URISyntaxException;
}
