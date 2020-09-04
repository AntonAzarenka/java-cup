package com.azarenka.jc.web;

import com.azarenka.jc.domain.auth.JwtResponse;
import com.azarenka.jc.domain.auth.LoginForm;
import com.azarenka.jc.domain.auth.ResponseMessage;
import com.azarenka.jc.domain.auth.SignUpForm;
import com.azarenka.jc.service.api.IUserService;
import com.azarenka.jc.service.evaluator.UserEvaluator;
import com.azarenka.jc.web.auth.JwtProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Authentication controller.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserEvaluator userEvaluator;
    @Value("${success.activate.url}")
    private String successUrl;

    /**
     * End point to sign in of user.
     *
     * @param loginRequest login form
     * @return instance of {@link ResponseEntity}
     */
    @PreAuthorize(value = "@userEvaluator.checkActivate(#loginRequest)")
    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        LOGGER.info(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    /**
     * End point to sign up of user.
     *
     * @param signUpRequest sign up form
     * @return instance of {@link ResponseEntity}
     */
    @PreAuthorize(value = "@userEvaluator.check(#signUpRequest)")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        userEvaluator.check(signUpRequest);
        userService.save(signUpRequest);
        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{code}")
    public String activate(@PathVariable("code") String code) {
        if (userService.activating(code)) {
            return "<h1 style='text-align: center'><a href='" + successUrl + "'>Вы успешно зарегистрированы." +
                " Пройдите по ссылке</a></h1>";
        }
        return "<h1>Данный активационный код не найден</h1>";
    }
}
