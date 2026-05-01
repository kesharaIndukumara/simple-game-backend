package com.navishkadarshana.smileygame.dto.player;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerReqDto {
    private String userName;
    private String password;
    private String email;
}
