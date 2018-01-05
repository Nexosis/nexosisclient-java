package com.nexosis.impl;

import com.neovisionaries.i18n.CountryCode;
import com.nexosis.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Views {

    public static ViewInfo createWithDataSetsAndColumsDef(String dataSetName, String rightDataSetName, Columns columnDef) {
        Argument.IsNotNullOrEmpty(dataSetName,"dataSetName");
        Argument.IsNotNullOrEmpty(rightDataSetName,"rightDataSetName");

        ViewInfo definition = new ViewInfo();

        definition.setDataSetName(dataSetName);
        definition.setColumns(columnDef);

        Join join = new Join();
        join.setDataSetName(rightDataSetName);

        List<Join> joins = new ArrayList<Join>();
        joins.add(join);
        definition.setJoins(joins);
        return definition;
    }

    public static ViewInfo createWithDataSetNames(String dataSetName, final String rightDataSetName) {
        Argument.IsNotNullOrEmpty(dataSetName,"dataSetName");
        Argument.IsNotNullOrEmpty(rightDataSetName,"rightDataSetName");

        ViewInfo definition = new ViewInfo();
        definition.setDataSetName(dataSetName);

        definition.setJoins(
                new ArrayList<Join>() {{
                    add(new Join() {{setDataSetName(rightDataSetName);}});
                }
                });

        return definition;
    }

    public static ViewInfo createWithICALUri(String dataSetName, URI iCalUri, java.util.TimeZone timeZone, Columns columnDef) {
        CalendarJoinSource joinSource = new CalendarJoinSource(null,iCalUri, timeZone);
        return createViewInfoWithCalendar(dataSetName,joinSource,columnDef);
    }

    public static ViewInfo createWithHolidayCalendarForCountry(String dataSetName, CountryCode calendarCountry, java.util.TimeZone timeZone, Columns columnDef) {
        CalendarJoinSource joinSource = new CalendarJoinSource(calendarCountry,null, timeZone);
        return createViewInfoWithCalendar(dataSetName, joinSource, columnDef);
    }

    public static ViewInfo createViewInfoWithCalendar(String dataSetName, final CalendarJoinSource calendarJoin, Columns columnDef) {
        Argument.IsNotNullOrEmpty(dataSetName,"dataSetName");

        ViewInfo info = new ViewDefinition();
        info.setDataSetName(dataSetName);
        info.setColumns(columnDef);
        info.setJoins(new ArrayList<Join>() {
            { add(new Join() {
                {setCalendar(calendarJoin); }
            });
            }});

        return info;
    }
}
