package com.myboard.controller;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.BoardTagDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping({"/read","/modify"})
    public void read(@RequestParam("bno") Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("Read or Modify...........");

        BoardDTO boardDTO = boardService.getBoard(bno);

        boardDTO.setTags(boardService.getTags(boardDTO).getTags());

        String tags = String.join(" #", boardDTO.getTags());

        model.addAttribute("board", boardDTO);
        model.addAttribute("tags", tags);
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes rattr) {
        log.info("Remove......");
        long msg = bno;
        boardService.removeBoards(bno);
        rattr.addFlashAttribute("deleteMsg", msg);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, RedirectAttributes rattr) {
        log.info("modify method post.............");

        log.info("boardDTO : " + boardDTO);

        boardService.modifyBoards(boardDTO);

        rattr.addAttribute("bno", boardDTO.getBno());
        rattr.addFlashAttribute("modifyMsg",boardDTO.getBno());

        return "redirect:/board/read";
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, BoardTagDTO boardTagDTO, RedirectAttributes rattr) {
        log.info("BoardDTO : " + boardDTO);

        // #-separated 태그를 리스트로 변환
        String tagsString = boardTagDTO.getTags();
        if (tagsString != null && !tagsString.isEmpty()) {
            List<String> tags = Arrays.stream(tagsString.split("\\s*#\\s*"))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty()) // 빈 문자열 제거
                    .collect(Collectors.toList());
            boardDTO.setTags(tags);
        }

        Long bno = boardService.register(boardDTO);

        rattr.addFlashAttribute("registerMsg", bno);

        return "redirect:/board/list";
    }



    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list..................");
        System.out.println("pageRequestDTO = " + pageRequestDTO);
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : result.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        result.setDtoList(resultWithTags);
        System.out.println("result = " + result);
        model.addAttribute("result", result);
    }

    @GetMapping("/myPage")
    public void myPage(PageRequestDTO pageRequestDTO, Model model) {
        log.info("myPage..................");
        System.out.println("pageRequestDTO = " + pageRequestDTO);
        PageResultDTO<BoardDTO, Object[]> result = boardService.getListMyPage(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : result.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        result.setDtoList(resultWithTags);
        System.out.println("result = " + result);
        model.addAttribute("result", result);
    }
}
