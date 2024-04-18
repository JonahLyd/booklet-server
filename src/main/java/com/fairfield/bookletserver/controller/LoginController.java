package com.fairfield.bookletserver.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@Controller
public class LoginController {
  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "login";
  }

  @PostMapping("/login")
  public void authorize(String username, String password, Model model) {
    System.out.println(username);
    System.out.println(password + " PASSSSS");
    model.addAttribute("authenticated", true);
  }
}
