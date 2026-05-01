package com.navishkadarshana.smileygame.service.authService;


import com.navishkadarshana.smileygame.config.security.custom.CustomOauthException;
import com.navishkadarshana.smileygame.entity.Player;
import com.navishkadarshana.smileygame.entity.Score;
import com.navishkadarshana.smileygame.enums.common.AccountVerifyStatus;
import com.navishkadarshana.smileygame.enums.common.ActiveStatus;
import com.navishkadarshana.smileygame.enums.common.UserRole;
import com.navishkadarshana.smileygame.exception.dto.CustomAuthenticationException;
import com.navishkadarshana.smileygame.exception.dto.CustomServiceException;
import com.navishkadarshana.smileygame.config.security.SecurityConstants;
import com.navishkadarshana.smileygame.constants.AppConstants;
import com.navishkadarshana.smileygame.dto.auth.*;
import com.navishkadarshana.smileygame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final PlayerRepository playerRepository;

    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User login: " + username);
       /* gets current authentication principal */
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String clientId = user.getUsername();

        switch (clientId) {

            case SecurityConstants.ADMIN_ID:
                return null;

            case SecurityConstants.PLAYER_ID:
                Player player = this.getPlayer(username);
                return new UserAuthDto(player.getId(), username, player.getPassword(), getRole(UserRole.USER),
                        modelMapper.map(player, PlayerAuthDto.class));

            default:
                throw new CustomOauthException(AppConstants.ErrorConstants.INVALID_CLIENT_ID);

        }
    }



    private Player getPlayer(String username) {
        Player player = playerRepository.findFirstByUserName(username).orElseThrow(() -> new CustomServiceException("Invalid username!"));
        if (player.getEmail_verified()== AccountVerifyStatus.NOT_VERIFY)
            throw new CustomServiceException("Your account is not yet verified. Please verify your account first!");
        return player;
    }



    private List<SimpleGrantedAuthority> getRole(UserRole userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }


}

