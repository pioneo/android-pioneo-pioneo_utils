package de.pioneo.pioutils.fitbit.api.services;

import android.app.Activity;
import android.content.Loader;

import de.pioneo.pioutils.fitbit.api.exceptions.MissingScopesException;
import de.pioneo.pioutils.fitbit.api.exceptions.TokenExpiredException;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderFactory;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderResult;
import de.pioneo.pioutils.fitbit.api.models.Device;
import de.pioneo.pioutils.fitbit.authentication.Scope;


/**
 * Created by jboggess on 9/14/16.
 */
public class DeviceService {

    private final static String DEVICE_URL = "https://api.fitbit.com/1/user/-/devices.json";
    private static final ResourceLoaderFactory<Device[]> USER_DEVICES_LOADER_FACTORY = new ResourceLoaderFactory<>(DEVICE_URL, Device[].class);

    public static Loader<ResourceLoaderResult<Device[]>> getUserDevicesLoader(Activity activityContext) throws MissingScopesException, TokenExpiredException {
        return USER_DEVICES_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.settings});
    }

}
