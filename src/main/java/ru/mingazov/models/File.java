package ru.mingazov.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class File {

    private Long id;
    private String name;

    public String getType() {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    // without uuid which added for storing
    public String getClearName() { return name.substring(name.indexOf(" ") + 1); }

}
