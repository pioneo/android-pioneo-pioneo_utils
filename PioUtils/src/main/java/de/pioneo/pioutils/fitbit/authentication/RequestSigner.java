package de.pioneo.pioutils.fitbit.authentication;


import de.pioneo.pioutils.fitbit.network.BasicHttpRequestBuilder;

/**
 * Created by jboggess on 9/26/16.
 */

public interface RequestSigner {

    void signRequest(BasicHttpRequestBuilder builder);

}
