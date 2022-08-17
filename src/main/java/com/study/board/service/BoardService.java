package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    // 오토와이어드로 의존성 주입
    // 글작성
    public void write(Board board, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID(); // 파일 식별자, 랜덤 이름
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, "name");
        file.transferTo(saveFile);

        board.setFile_name(fileName);
        board.setFile_path("/files/"+fileName);

        boardRepository.save(board);
    }
    // 게시글 목록보기
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll((org.springframework.data.domain.Pageable) pageable); // 보드라는 리스트가 담긴 클래스를 그대로 반환해줌
        // findall 모든!! 걸 반환하는 클래스
    }
    //상세보기
    public Board boardView(Integer id) {
        // 파인드바이아이디는 인티저를 받음
        // 꼭 겟으로 받아야 하는 이유는 뭐임? 옵셔널 값이랬는데
        return boardRepository.findById(id).get();
    }

    //글삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
