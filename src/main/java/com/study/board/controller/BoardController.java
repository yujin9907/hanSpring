package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writedo")
    public String boardWriteDo(Board board){
    //매개변수를 일일이 string title, content 이런 식으로 안 받고
        // 디비연결할 때 엔티티에 생성한 테이블 클래스를 그대로 객체로
        // 받아와서 사용함
        // 게터세터 구현할 필요 없이 사용 가능
        boardService.write(board);
        return "";
    }
}
