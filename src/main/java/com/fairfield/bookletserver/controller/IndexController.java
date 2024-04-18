package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.entity.Booklet;
import com.fairfield.bookletserver.repository.BookletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
  private static final String CONSTANT_PATH_TO_BOOKLET = "/booklet?fileName=%s&level=%d";
  @Autowired
  BookletRepository bookletRepository;

  @GetMapping(value = {"/", "/index"})
  public String getIndex(Model model) {
    List<Booklet> recentBooklets  = bookletRepository.getRecentBooklets();
    int index = 1;
    for (Booklet booklet : recentBooklets) {
      String name = "fileName" + index;
      String path = "filePath" + index;
      model.addAttribute(name, booklet.getFileName());
      model.addAttribute(path, String.format(CONSTANT_PATH_TO_BOOKLET, booklet.getFileName(), booklet.getLevelId()));
    }
    return "index";
  }
}
