package com.nexosis.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ViewDeleteCriteria {
    private String name;
    private EnumSet<ViewCascadeOptions> cascade;

    public ViewDeleteCriteria(String name)
    {
        this.setName(name);
    }

    /**
     * The name of the View to delete
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Options for cascading the delete.
     *
     * When ViewCascadeOptions.CASCADE_SESSIONS, also deletes sessions associated with the view
     */
    public EnumSet<ViewCascadeOptions> getCascade() {
        return cascade;
    }

    public void setCascade(EnumSet<ViewCascadeOptions> cascade) {
        this.cascade = cascade;
    }

    public Map<String,Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();

        if (cascade != null) {
            ArrayList<String> set = new ArrayList<>();
            if (cascade.contains(ViewCascadeOptions.CASCADE_SESSIONS))
                set.add(ViewCascadeOptions.CASCADE_SESSIONS.value());

            if (set.size() > 0) {
                parameters.put("cascade",set);
            }
        }

        return parameters;
    }
}
