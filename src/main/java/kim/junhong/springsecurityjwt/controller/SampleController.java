package kim.junhong.springsecurityjwt.controller;

import kim.junhong.springsecurityjwt.service.MyUserDetailsService;
import kim.junhong.springsecurityjwt.util.JwtUtil;
import kim.junhong.springsecurityjwt.vo.AuthRequest;
import kim.junhong.springsecurityjwt.vo.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SampleController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/sample")
    public String sample() {
        return "Sample";
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String accessToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(accessToken));
    }
}
