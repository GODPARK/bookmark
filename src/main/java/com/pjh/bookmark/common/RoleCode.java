package com.pjh.bookmark.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleCode {
    DEFAULT_USER_ROLE(1),
    USER_USER_ROLE(11),
    ADMIN_USER_ROLE(100),
    SUPER_ADMIN_USER_ROLE(1000);
    private final int role;
}
