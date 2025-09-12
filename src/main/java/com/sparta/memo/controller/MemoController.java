package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//메모 생성하기 api
@RestController //html을 따로 반환하지 않을 것이기 때문에 //ajax요청을 받을 것임
@RequestMapping("/api")
public class MemoController {

    //임시 저장소
    private final Map<Long, Memo> memoList = new HashMap<>(); //키: memo의 id값

    //메모 생성하기 (클라이언트에서 json 데이터를 서버로 가져옴(requestDto) -> Memo 엔티티에 데이터 넣기 -> ResponseDto로 변환하기
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) { //requestDto : 클라이언트가 보내준 데이터
        //RequestDto -> Entity로 변환
        Memo memo = new Memo(requestDto); //requestDto객체에서 정보를 가져와 memo에 저장

        //Memo에서 id는 직접생성
        //Memo의 Max Id를 Check하기. (Id가 중복되지 않도록 가장 마지막값을 찾고, +1로 생성해주도록
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        //DB 저장
        memoList.put(memo.getId(), memo);

        //Entity -> ResponseDto 변환
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }


    //메모 조회하기
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() { //Memo들(MemoResponseDto)의 목록이라 List형식
        //Map To List
        List<MemoResponseDto> responseList = memoList.values().stream() //.stream 요소를 하나씩 꺼내서 for문처럼 돌려줌
                .map(MemoResponseDto::new).toList(); //map은 stream에서 가져온 요소(memo)를 (ResponseDto)로 변환
        //이때 (MemoResponseDto::new) 이기 때문에 map에서 가져온 요소(memo)를 가진 생성자가 호출되어서 MemoResponseDto객체의 memo
        //파라미터를 가진 생성자가 홀출됨

        return responseList;
    }
}
