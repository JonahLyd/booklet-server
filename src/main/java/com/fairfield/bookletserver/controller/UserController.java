package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
  @Autowired
  UserRepository userRepository;

  @GetMapping("/user")
  public String getUser(Model model) {
    model.addAttribute("user", userRepository.getUserFromContext());
    model.addAttribute("headerText", "User Profile");
    return "user";
  }
}
