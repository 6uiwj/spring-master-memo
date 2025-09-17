//package com.sparta.memo.entity;
//
//import com.sparta.memo.dto.MemoRequestDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class Memo {
//    private Long id;
//    private String username;
//    private String contents;
//
//    //RequestDto를   Entity로 만들어주기
//    public Memo(MemoRequestDto requestDto) { //클라이언트에서 받아온 requestDto데이터를 Memo에 넣어줌
//        this.username = requestDto.getUsername();
//        this.contents = requestDto.getContents();
//    }
//
//    public void update(MemoRequestDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.contents = requestDto.getContents();
//    }
//}


package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "memo") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    public Memo(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}
