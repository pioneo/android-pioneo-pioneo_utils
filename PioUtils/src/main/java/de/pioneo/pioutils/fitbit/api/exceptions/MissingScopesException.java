package de.pioneo.pioutils.fitbit.api.exceptions;


import java.util.Collection;

import de.pioneo.pioutils.fitbit.authentication.Scope;

/**
 * Created by jboggess on 9/19/16.
 */
public class MissingScopesException extends FitbitAPIException {

    private Collection<Scope> scopes;

    public MissingScopesException(Collection<Scope> scopes) {
        this.scopes = scopes;
    }

    public Collection<Scope> getScopes() {
        return scopes;
    }
}
