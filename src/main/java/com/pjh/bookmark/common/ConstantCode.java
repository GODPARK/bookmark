package com.pjh.bookmark.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantCode {
    ACTIVE_USER_STATE(1),
    DEACTIVE_USER_STATE(0),
    ACTIVE_TOKEN_STATE(1),
    DEACTIVE_TOKEN_STATE(0),
    DEFAULT_USER_ROLE(1);

    private final int state;
}
