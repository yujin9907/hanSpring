package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // 페이지블 임포트 이거임!!!
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writedo")
    public String boardWriteDo(Board board, Model model, MultipartFile file) throws Exception{
    //매개변수를 일일이 string title, content 이런 식으로 안 받고
        // 디비연결할 때 엔티티에 생성한 테이블 클래스를 그대로 객체로
        // 받아와서 사용함
        // 게터세터 구현할 필요 없이 사용 가능
        boardService.write(board, file);
        model.addAttribute("message", "글 작성 완료");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
        // 사실 오류처리를 해줄 필요도 있음. 실패할시, 글 작성에 실패했다, 그런 거.
    }
    // 파일 업로드 : 원래 multipartHttpServletRequest => multipartFile 인터페이를 활용
    // create의 연결 로직 정리하고 read로 넘어아기

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        // 모델이라는 클래스가 뭔지? https://velog.io/@msriver/Spring-Model-%EA%B0%9D%EC%B2%B4
        // https://dev-coco.tistory.com/100
//        https://cupdisin.tistory.com/19
        //model.addAttribute("list", boardService.boardList());
        // 리스트라는 이름으로 모델을 서비스에 넘긴다 서비스보드리스트로

        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "boardList";
    }

//    즉 boardService.어쩌고를 통해서 보드서비스에 저장하거나, 데이터를 가져옴
//    모델은 데이터를 뷰와 이케 하는거 어트리뷰트(-란이름으로, -를 넘겨준다)
    // notion pageable 참고

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id){
        // 겟매핑 방식으로 view?id=1 이렇게 입력하면 1번 게시글을 볼 수 있음
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    // 글수정
    @GetMapping("/board/modify/{id}")
    // 패스베리어블 사용해보기 ; th에서 넘겨주는 거랑 다르게 깔끔하게 매핑없이 숫자만 넘어감
    public String boradModify(@PathVariable("id") Integer id, Model model){
        // 작성된 글을 그대로 보기 위해서 데이터를 보드라는 이름에 담아 넘겨주기
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }
    // 수정 처리
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id); // 기존글
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        // 게터세터를 어떻게 자동으로 받아오는 거임?
        boardService.write(boardTemp, file);
        return "redirect:/board/list";
    }
    @GetMapping("board/list")
    public String boardList() {
        return "boardList";
    }


    // 연습용

}
