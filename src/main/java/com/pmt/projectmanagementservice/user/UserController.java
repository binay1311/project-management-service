package com.pmt.projectmanagementservice.user;

import com.pmt.projectmanagementservice.constants.StatusCode;
import com.pmt.projectmanagementservice.models.Response;
import com.pmt.projectmanagementservice.payload.JWTLoginSuccessResponse;
import com.pmt.projectmanagementservice.payload.LoginRequest;
import com.pmt.projectmanagementservice.project.Project;
import com.pmt.projectmanagementservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.pmt.projectmanagementservice.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public Response<?> registerUser(@RequestBody User user) throws IllegalAccessException {
        return Response.<User>builder()
                .response(userService.saveUser(user))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }


}
