package de.pioneo.pioutils.fitbit.api.services;

import android.app.Activity;
import android.content.Loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.pioneo.pioutils.fitbit.api.exceptions.MissingScopesException;
import de.pioneo.pioutils.fitbit.api.exceptions.TokenExpiredException;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderFactory;
import de.pioneo.pioutils.fitbit.api.loaders.ResourceLoaderResult;
import de.pioneo.pioutils.fitbit.api.models.WeightLogs;
import de.pioneo.pioutils.fitbit.authentication.Scope;

/**
 * Created by jboggess on 10/3/16.
 */
public class WeightService {

    private final static String WEIGHT_URL = "https://api.fitbit.com/1/user/-/body/log/weight/date/%s/%s.json";
    private static final ResourceLoaderFactory<WeightLogs> WEIGHT_LOG_LOADER_FACTORY = new ResourceLoaderFactory<>(WEIGHT_URL, WeightLogs.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static Loader<ResourceLoaderResult<WeightLogs>> getWeightLogLoader(Activity activityContext, Date startDate, int calendarDateType, int number) throws MissingScopesException, TokenExpiredException {
        String periodSuffix = "d";
        switch (calendarDateType) {
            case Calendar.WEEK_OF_YEAR:
                periodSuffix = "w";
                break;
            case Calendar.MONTH:
                periodSuffix = "m";
                break;
        }

        return WEIGHT_LOG_LOADER_FACTORY.newResourceLoader(
                activityContext,
                new Scope[]{Scope.weight},
                dateFormat.format(startDate),
                String.format(Locale.US, "%d%s", number, periodSuffix));
    }

}
