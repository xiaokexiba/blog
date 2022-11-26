package com.yeff.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户DTO
 *
 * @author xoke
 * @date 2022/11/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    public String email;
}

