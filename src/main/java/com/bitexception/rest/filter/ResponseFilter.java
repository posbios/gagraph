package com.bitexception.rest.filter;

import com.bitexception.rest.filter.ServerResponse;

/**
 * Configure and manage your filters as arrays of strings.
 *
 */
public class ResponseFilter extends Filter<ServerResponse> {

    /**
     * Load default filter
     */
    public ResponseFilter() {
        super("msg", "type", "result");
    }
}
