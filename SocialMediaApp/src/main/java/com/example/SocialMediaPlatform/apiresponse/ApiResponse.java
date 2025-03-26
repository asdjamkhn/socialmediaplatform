package com.example.SocialMediaPlatform.apiresponse;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private String message;
    private int status;
    private Object data;

}
