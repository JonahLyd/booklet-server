package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.entity.Booklet;
import com.fairfield.bookletserver.repository.BookletRepository;
import com.fairfield.bookletserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;

@Controller
public class BookletController {
  public static final String CONSTANT_BOOKLET_PATH = "/files/level%d/%s";
  private final Path root1 = Paths.get("./src/main/resources/static/files/level1");
  private final Path root2 = Paths.get("./src/main/resources/static/files/level2");

  @Autowired
  BookletRepository bookletRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  IndexController indexController;

  @GetMapping("/booklet")
  public String getBookletPage(@RequestParam String fileName, Model model) {
    Booklet booklet = bookletRepository.getBookletByName(fileName);
    if (booklet != null) {
      model.addAttribute("canView", true);
      model.addAttribute("path", String.format(CONSTANT_BOOKLET_PATH, booklet.getLevelId(), booklet.getFileName()));
      model.addAttribute("previous", booklet.getFileName());
      userRepository.updateRecentBooklet(booklet.getId());
    } else {
      model.addAttribute("message", "You do not have the authority to view this file.");
    }

    model.addAttribute("headerText", "View A Booklet");
    model.addAttribute("user", userRepository.getUserFromContext());

    return "booklet";
  }

  @PostMapping("/upload")
  public String uploadBookletFile(MultipartFile file, String level, Model model) {
    if (file == null || file.isEmpty() || file.getOriginalFilename() == null
        ||file.getOriginalFilename().isEmpty()) {
      return indexController.getIndex(model);
    }
    bookletRepository.insertBooklet(Booklet.newBuilder()
        .withLevelId(Long.valueOf(level))
        .withFileName(file.getOriginalFilename())
        .withCreated(Date.from(Instant.now()))
        .withUpdated(Date.from(Instant.now()))
        .build());
    try {
      var inputStream = file.getInputStream();
      var fileName = file.getOriginalFilename();
      switch(level) {
        case "1" -> Files.copy(inputStream, this.root1.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        case "2" -> Files.copy(inputStream, this.root2.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (Exception ignored) {
    }

    return indexController.getIndex(model);
  }

}
