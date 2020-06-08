package de.pioneo.pioutils.fitbit.api.services;

import android.app.Activity;
import android.content.Loader;
import de.pioneo.pioutils.fitbit.api.exceptions.MissingScopesException;
import de.pioneo.pioutils.fitbit.api.exceptions.TokenExpiredException;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderFactory;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderResult;
import de.pioneo.pioutils.fitbit.api.models.Device;
import de.pioneo.pioutils.fitbit.api.models.UserContainer;
import de.pioneo.pioutils.fitbit.authentication.Scope;

/**
 * Created by jboggess on 9/14/16.
 */
public class UserService {

    private final static String USER_URL = "https://api.fitbit.com/1/user/-/profile.json";
    private static final ResourceLoaderFactory<UserContainer> USER_PROFILE_LOADER_FACTORY = new ResourceLoaderFactory<>(USER_URL, UserContainer.class);

    public static Loader<ResourceLoaderResult<UserContainer>> getLoggedInUserLoader(Activity activityContext) throws MissingScopesException, TokenExpiredException {
        return USER_PROFILE_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.profile});
    }

}
