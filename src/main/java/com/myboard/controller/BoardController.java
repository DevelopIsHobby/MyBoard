package com.myboard.controller;

import com.myboard.dto.BoardDTO;
import com.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, RedirectAttributes rattr) {
        log.info("BoardDTO : " + boardDTO);

        Long bno = boardService.register(boardDTO);

        rattr.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/", "list"})
    public String list() {
        log.info("list..................");

        return "/board/list";
    }


}
