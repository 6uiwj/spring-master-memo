package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

//    //1. "Bean" 이름으로 가져오기
//    public MemoService(ApplicationContext context) {
//        //1. 'Bean'의 이름으로 가져오기
//        MemoRepository memoRepository = (MemoRepository) context.getBean("memoRepository");
//        this.memoRepository = memoRepository;
//    }

//    //2.  Bean 클래스 형식으로 가져오기
//    public MemoService(ApplicationContext context) {
//        MemoRepository memoRepository = context.getBean(MemoRepository.class);
//        this.memoRepository = memoRepository;
//    }

//    //메서드로 의존 주입
//    @Autowired
//    public void setDi(MemoRepository memoRepository) {
//        this.memoRepository = memoRepository;
//    }

    //private final JdbcTemplate jdbcTemplate; //final 이니 초기화 필요 -> 생성자
    //private final MemoRepository memoRepository;

    //제어의 역전 코드 변경 (약한 결합)
//    //생성자
//    public MemoService(JdbcTemplate jdbcTemplate) { //jdbcTemplate 초기화하는 생성자
//        //this.jdbcTemplate = jdbcTemplate; //JdbcTemplate을 받아온 다음에 주입하여 초기화
//        /**
//         *  각 메서드 앞에 있는
//         *  MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
//         *  를 여기서 한번만 주입받고 중복코드 지워준다.
//         */
//        this.memoRepository = new MemoRepository(jdbcTemplate);
//    }

    public MemoService(MemoRepository memoRepository) { //memoRepository를 데려와서 필드에 주입
        this.memoRepository = memoRepository;
    }


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {

// RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        //MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        //리포지토리로 이관
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

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        //MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        //return memoRepository.findAll();

        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();

        //리포지토리로 이관
//        String sql = "SELECT * FROM memo";
//
//        //jdbcTemplate.query: SQL을 실행하고 결과를 매핑하여 List<T>로 반환하는 메서드.
//        //익명 내부 클래스 (스프링 제공 인터페이스인 RowMapper 구현)
//        //ResultSet의 각 행을 어떻게 DTO로 변환할지 알려줌
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

    @Transactional //변경 감지
    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        //MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // if(memo != null) {
        // memo 내용 수정

        memo.update(requestDto);
        //리포지토리로 이관
//            String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
//            jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);

        return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
    }

    //리포지토리로 이관
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

    public Long deleteMemo(Long id) {
        //MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        //메모 삭제
        memoRepository.delete(memo);
        return id;


    }
    //공통부분 - 메모 조회
    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() -> //null check
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
