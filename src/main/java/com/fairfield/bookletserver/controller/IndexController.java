package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.entity.Booklet;
import com.fairfield.bookletserver.entity.BookletSearchResponse;
import com.fairfield.bookletserver.repository.BookletRepository;
import com.fairfield.bookletserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
  public static final String CONSTANT_PATH_TO_BOOKLET = "/booklet?fileName=%s&level=%d";
  @Autowired
  BookletRepository bookletRepository;
  @Autowired
  UserRepository userRepository;

  @GetMapping(value = {"/", "/index"})
  public String getIndex(Model model) {
    var recentBooklets  = userRepository.getRecentBookletIds();
    var index = 1;
    for (Long bookletId : recentBooklets) {
      var booklet = bookletRepository.getBookletById(bookletId);
      var name = "fileName" + index;
      var path = "filePath" + index;
      model.addAttribute(name, booklet.getFileName());
      model.addAttribute(path, String.format(CONSTANT_PATH_TO_BOOKLET, booklet.getFileName(), booklet.getLevelId()));
      index += 1;
    }
    model.addAttribute("user", userRepository.getUserFromContext());
    model.addAttribute("previous", "lorem ipsum");
    model.addAttribute("isNotSearch", true);
    model.addAttribute("isSearch", false);
    model.addAttribute("headerText", "Search for a Booklet");
    return "index";
  }

  @RequestMapping(path = {"/", "/search"})
  public String home(Booklet booklet, Model model, String keyword) {
    List<BookletSearchResponse> list;
    if (keyword != null) {
      list = bookletRepository.findByKeyword(keyword);
    } else {
      list = bookletRepository.getAllBookletPaths();
    }
    model.addAttribute("user", userRepository.getUserFromContext());
    model.addAttribute("previous", keyword);
    model.addAttribute("list", list);
    model.addAttribute("isNotSearch", false);
    model.addAttribute("isSearch", true);
    model.addAttribute("headerText", "");
    return "index";
  }
}
