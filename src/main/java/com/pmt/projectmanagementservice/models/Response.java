package com.pmt.projectmanagementservice.models;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    /** Stores the response code of each Response eg., 200, 800 */
    private Integer responseCode;
    /** Stores the response message of each Response eg., Unidentified error, Database Fetch Error */
    private String responseMessage;
    /** Stores the output response of any passed class type eg., List, String */
    private T response;
}
