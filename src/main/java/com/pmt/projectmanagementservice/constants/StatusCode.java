package com.pmt.projectmanagementservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    /** Denotes a successful reponse {@code code} : 200 {@code message} : "Success" */
    SUCCESS(200, "Success"),

    /** Denotes a failed reponse {@code code} : 600 {@code message} : "Failed" */
    FAILED(600, "Failed");

    /** Stores the success code for any given success type eg., {@code SUCCESS} */
    private final int code;
    /** Stores the success message for any given success type eg., {@code SUCCESS} */
    private final String message;
}
