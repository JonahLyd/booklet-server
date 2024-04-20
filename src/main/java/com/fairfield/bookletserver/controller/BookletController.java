package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.entity.Booklet;
import com.fairfield.bookletserver.repository.BookletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookletController {
  public static final String CONSTANT_BOOKLET_PATH = "/images/fileImages/level%d/%s";

  @Autowired
  BookletRepository bookletRepository;

  @GetMapping("/booklet")
  public String getBookletPage(@RequestParam String fileName, Model model) {
    Booklet booklet = bookletRepository.getBookletByName(fileName);
    model.addAttribute("path",
        String.format(CONSTANT_BOOKLET_PATH, booklet.getLevelId(), booklet.getFileName()));
    model.addAttribute("previous", booklet.getFileName());
    return "booklet";
  }

}
