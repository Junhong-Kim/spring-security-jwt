package kim.junhong.springsecurityjwt.vo;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String username;
    private String password;
}
