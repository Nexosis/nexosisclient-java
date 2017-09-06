package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;

import java.net.URI;
import java.security.InvalidParameterException;
import java.util.TimeZone;

/**
 * Use calendar events as a join data source in Views
 * @see <a href="http://docs.nexosis.com/guides/calendars">http://docs.nexosis.com/guides/calendars</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalendarJoinSource {
    private static final String NEXOSIS_CAL_NAMESPACE = "Nexosis.Holidays-";
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private URI url;
    @JsonProperty("timeZone")
    private String timeZone;

    public CalendarJoinSource(){

    }

    public CalendarJoinSource(String name, URI url, String timeZone) {
        this.name = name;
        this.url = url;
        this.timeZone = timeZone;
    }

    public CalendarJoinSource(CountryCode country, URI url, TimeZone timeZone) {
        if(country != null)
            this.name = NEXOSIS_CAL_NAMESPACE + country.getAlpha2();
        this.url = url;
        if(timeZone != null)
            this.timeZone = timeZone.getID();
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of a well-known calendar from Nexosis. Exclusive of url
     * @param name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("url")
    public URI getUrl() {
        return url;
    }

    /**
     * The url of an iCal calendar to download. Exclusive of name.
     * @return
     */
    @JsonProperty("url")
    public void setUrl(URI url) {
        this.url = url;
    }

    @JsonProperty("timeZone")
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * An optional time zone (tz-db) name for this calendar's events
     * @param timeZone
     */
    @JsonProperty("timeZone")
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
