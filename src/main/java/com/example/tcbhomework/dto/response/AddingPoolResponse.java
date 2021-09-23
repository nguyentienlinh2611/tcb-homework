package com.example.tcbhomework.dto.response;

import com.example.tcbhomework.dto.AddingPoolStatusEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddingPoolResponse {
    private AddingPoolStatusEnum status;
}
