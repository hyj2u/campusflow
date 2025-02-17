package com.cnco.campusflow.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private T data;

    public static <T> CommonResponse<T> of(T data) {
        return new CommonResponse<>(data);
    }
}
