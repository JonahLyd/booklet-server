package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@Controller
public class LoginController {
  @Autowired
  UserRepository userRepository;

  @GetMapping(value = {"/login"})
  public String getLogin() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    SecurityContextHolder.clearContext();
    return "login";
  }

  @PostMapping("/authenticate")
  public void authorize(String username, String password, Model model) {
    var user = userRepository.getUserByAuth(username + ":" + password);
    if (user == null) {
      model.addAttribute("authenticated", false);
      model.addAttribute("errorMessage", "The username or password does not match.");
    } else {
      SecurityContextHolder.getContext().setAuthentication(user);
      model.addAttribute("authenticated", true);
    }
  }
}
