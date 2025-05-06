package com.ulrichcoding.hrmi.email;

public enum EMailTemplateName {
    UPDATE_CONFIRMATION("update_confirmation");
    private final String name;

    EMailTemplateName(String name) {
        this.name = name;
    }
}
