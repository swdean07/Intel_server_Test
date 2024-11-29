package com.busanit501.helloworld.jdbcex.vo;

import lombok.*;

// @Data // getter/setter/tostring/hashcode/equals
//VO 에서는 가급적  setter 보다, getter위주로 작업을 함, 불변성 유지 하기위해서.

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
