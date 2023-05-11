package ru.mingazov.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Admin {

    private long id;
    private String login;
    private String password;

}
