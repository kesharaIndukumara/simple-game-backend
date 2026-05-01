package com.navishkadarshana.smileygame.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private String user_name;
    private String mobile_number;
    private String[] scope;
    private String userRole;
    private Long lastLogoutTime;
    private String ati;
    private String client_id;
    private Long userId;
    private long exp;
    private String[] authorities;
    private String jti;


}
