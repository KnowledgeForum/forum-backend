package com.project.forum.type;

import lombok.Getter;

@Getter
public enum BoardTypeEnum {
    N("NEWS"),
    B("BOARD");

    private final String name;

    BoardTypeEnum(String name) {
        this.name = name;
    }
}
