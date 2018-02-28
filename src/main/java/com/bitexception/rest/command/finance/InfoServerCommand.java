package com.bitexception.rest.command.finance;

import com.bitexception.rest.command.UriCommand;
import com.bitexception.nio.HttpConversation;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Show server information
 */
public class InfoServerCommand extends UriCommand {

    private static final String PATTERN = "kie-server/services/rest/server";
    private static final HttpConversation.RequestMethod requestMethod = HttpConversation.RequestMethod.GET;

    /**
     *
     */
    public InfoServerCommand() {
        super(PATTERN);
    }

    /**
     * Execute an conversation with server with configured_: query, query params
     * and data
     *
     * @param parameters no params
     * @return json object with server information
     * @throws IOException http comunication errors
     * @throws URISyntaxException if something is wrong on configuration url
     * will be generated incorrectly
     */
    @Override
    public String conversation(String parameters) throws IOException, URISyntaxException {
        return new HttpConversation(this).setRequestMethod(requestMethod).conversation();
    }
}
