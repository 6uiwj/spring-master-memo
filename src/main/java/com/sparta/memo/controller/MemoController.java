//package com.sparta.memo.controller;
//
//import com.sparta.memo.dto.MemoRequestDto;
//import com.sparta.memo.dto.MemoResponseDto;
//import com.sparta.memo.entity.Memo;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////메모 생성하기 api
//@RestController //html을 따로 반환하지 않을 것이기 때문에 //ajax요청을 받을 것임
//@RequestMapping("/api")
//public class MemoController {
//
//    //임시 저장소
//    private final Map<Long, Memo> memoList = new HashMap<>(); //키: memo의 id값
//
//    //메모 생성하기 (클라이언트에서 json 데이터를 서버로 가져옴(requestDto) -> Memo 엔티티에 데이터 넣기 -> ResponseDto로 변환하기
//    @PostMapping("/memos")
//    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) { //requestDto : 클라이언트가 보내준 데이터
//        //RequestDto -> Entity로 변환
//        Memo memo = new Memo(requestDto); //requestDto객체에서 정보를 가져와 memo에 저장
//
//        //Memo에서 id는 직접생성
//        //Memo의 Max Id를 Check하기. (Id가 중복되지 않도록 가장 마지막값을 찾고, +1로 생성해주도록
//        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
//        memo.setId(maxId);
//
//        //DB 저장
//        memoList.put(memo.getId(), memo);
//
//        //Entity -> ResponseDto 변환
//        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
//
//        return memoResponseDto;
//    }
//
//
//    //메모 조회하기
//    @GetMapping("/memos")
//    public List<MemoResponseDto> getMemos() { //Memo들(MemoResponseDto)의 목록이라 List형식
//        //Map To List
//        List<MemoResponseDto> responseList = memoList.values().stream() //.stream 요소를 하나씩 꺼내서 for문처럼 돌려줌
//                .map(MemoResponseDto::new).toList(); //map은 stream에서 가져온 요소(memo)를 (ResponseDto)로 변환
//        //이때 (MemoResponseDto::new) 이기 때문에 map에서 가져온 요소(memo)를 가진 생성자가 호출되어서 MemoResponseDto객체의 memo
//        //파라미터를 가진 생성자가 홀출됨
//
//        return responseList;
//    }
//
//    //데이터 수정 -> 데이터 필요 -> client 바디 부분에서 데이터가 넘어옴 (json 형식) -> requestBody로 받기
//    @PutMapping("/memos/{id}") //PathVariable 방식, id만 반환할 것임
//    //id : 데이터 불러오기 위해 , requestDto json형식으로 넘어온 데이터를 받기 위해
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        // 해당 메모가 DB에 존재하는지 확인
//        if(memoList.containsKey(id)) { //contains 해당 키가 있는가? -> t/f
//            //해당 메모 가져오기
//            Memo memo = memoList.get(id);
//
//            //memo 수정
//            memo.update(requestDto);
//            return memo.getId();
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
//
//    @DeleteMapping("/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        //해당 메모가 DB에 존재하는지 확인
//        if(memoList.containsKey(id)) {
//            //해당 메모를 삭제
//            memoList.remove(id);
//            return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
//
//}


package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//JDBC 템플릿 코드
@RestController
@RequestMapping("/api")
public class MemoController {


   //private final JdbcTemplate jdbcTemplate; //final 이니 초기화 필요 -> 생성자
    private final MemoService memoService;


    public MemoController(MemoService memoService) { //만들어진 서비스만 받아와서 필드에 주입
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        //MemoService memoService = new MemoService(jdbcTemplate); //인스턴스화
        return memoService.createMemo(requestDto); //서비스에서  비즈니스 로직 처리후 바로 리턴






//        //서비스로 옮길 코드
//        // RequestDto -> Entity
//        Memo memo = new Memo(requestDto);
//
//        // DB 저장
//        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체
//
//        String sql = "INSERT INTO memo (username, contents) VALUES (?, ?)";
//        jdbcTemplate.update( con -> { //insert, update, delete -> update 사용
//                    PreparedStatement preparedStatement = con.prepareStatement(sql,
//                            Statement.RETURN_GENERATED_KEYS);
//
//                    preparedStatement.setString(1, memo.getUsername()); //동적 쿼리에 넣을 데이터
//                    preparedStatement.setString(2, memo.getContents());
//                    return preparedStatement;
//                },
//                keyHolder);
//
//        // DB Insert 후 받아온 기본키 확인
//        Long id = keyHolder.getKey().longValue();
//        memo.setId(id);
//
//        // Entity -> ResponseDto
//        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
//
//        return memoResponseDto;
    }

    //이 컨트롤러가 RestController이므로 반환값이 자동으로 JSON으로 직렬화되어 클라이언트에 응답으로 나감
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        //MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMemos();
        //서비스로 이관
//        // DB 조회
//        String sql = "SELECT * FROM memo";
//
//        //jdbcTemplate.query: SQL을 실행하고 결과를 매핑하여 List<T>로 반환하는 메서드.
//                                        //익명 내부 클래스 (스프링 제공 인터페이스인 RowMapper 구현)
//                                        //ResultSet의 각 행을 어떻게 DTO로 변환할지 알려줌
//        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() { //조회 : query 메서드 사용
//
//            //mapRow: RowMapper의 핵심메서드, JDBCTemplate이 결과 집합의 각 행마다 이 메서드를  호출
//            //데이터를 row한줄한줄 받아옴 -> 결과를 ResultSet에 받음
//            @Override
//            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {   //rs: 현재 행
//                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
//                Long id = rs.getLong("id");
//                String username = rs.getString("username");
//                String contents = rs.getString("contents");
//                return new MemoResponseDto(id, username, contents); //한줄 완성, 추출한 값들로 DTO객체 하나를 생성해서 반환List<
//            }
//        });
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        //MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.updateMemo(id, requestDto);

        //서비스로 이관
//        // 해당 메모가 DB에 존재하는지 확인
//        Memo memo = findById(id);
//        if(memo != null) {
//            // memo 내용 수정
//            String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
//            jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
//
//            return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        //MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteMemo(id);

        //서비스로 이관
//        // 해당 메모가 DB에 존재하는지 확인
//        Memo memo = findById(id);
//        if(memo != null) {
//            // memo 삭제
//            String sql = "DELETE FROM memo WHERE id = ?";
//            jdbcTemplate.update(sql, id);
//
//            return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
    }

//서비스로이관
//    //Update와 Delete의 중복 코드를 따로 빼서 관리
//    //데이터베이스에 존재하는지 확인하는 코드
//    private Memo findById(Long id) {
//        // DB 조회
//        String sql = "SELECT * FROM memo WHERE id = ?";
//
//        return jdbcTemplate.query(sql, resultSet -> {
//            if(resultSet.next()) {
//                Memo memo = new Memo();
//                memo.setUsername(resultSet.getString("username"));
//                memo.setContents(resultSet.getString("contents"));
//                return memo; //데어터가 있을 때 메모를 반환
//            } else {
//                return null; //데이터가 없으면 Null 반환
//            }
//        }, id);
//    }
}