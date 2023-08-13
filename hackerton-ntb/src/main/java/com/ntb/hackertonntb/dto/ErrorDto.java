package com.ntb.hackertonntb.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorDto {
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}
