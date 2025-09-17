package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //자동 생성된 SimpleJpaRepository에 @Repository어노테이션이 붙어있기 때문에 여기선 어노테이션 안붙여도 됨
public interface MemoRepository extends JpaRepository<Memo, Long> {

        List<Memo> findAllByOrderByModifiedAtDesc();
        List<Memo> findAllByUsername(String username);









//    private final JdbcTemplate jdbcTemplate;
//
//    public MemoRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public Memo save(Memo memo) {
//        //DB저장
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
//        return memo;
//    }
//
//    public List<MemoResponseDto> findAll() {
//        //DB조회
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
//    }
//
//    //Update와 Delete의 중복 코드를 따로 빼서 관리
//    //데이터베이스에 존재하는지 확인하는 코드
//    public Memo findById(Long id) {
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
//
//    public void update(Long id, MemoRequestDto requestDto) {
//        String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
//        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
//    }
//
//    public void delete(Long id) {
//        // memo 삭제
//        String sql = "DELETE FROM memo WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    @Transactional //변경감지가 일어나 update 쿼리가 발생
//    public Memo createMemo(EntityManager em) {
//        Memo memo = em.find(Memo.class, 1);
//        memo.setUsername("Robbert");  //데이터 변경
//        memo.setContents("@Transactional 전파 테스트 중!2");
//
//        System.out.println("createMemo 메서드 종료");
//        return memo;
//    }
}
