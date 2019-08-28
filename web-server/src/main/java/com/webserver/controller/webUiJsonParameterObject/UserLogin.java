package com.webserver.controller.webUiJsonParameterObject;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Component
@Data
public class UserLogin {
    @NotEmpty(message = "loginid不能为空")
    @NotBlank(message = "loginid不能为空")
    private String name;

    @NotEmpty(message = "password不能为空")
    @NotBlank(message = "password不能为空")
    private String password;
}
