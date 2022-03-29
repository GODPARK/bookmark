package com.pjh.bookmark.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    ACTIVE_USER_STATE(1),
    DEACTIVE_USER_STATE(0),

    ACTIVE_TOKEN_STATE(1),
    DEACTIVE_TOKEN_STATE(0),

    ACTIVE_BOOKMARK_STATE(1),
    DEACTIVE_BOOKMARK_STATE(0),
    DEFAULT_BOOKMARK_NUM(1),

    ACTIVE_HASH_STATE(1),
    DEACTIVE_HASH_STATE(0)
    ;

    private final int state;
}
