package com.fairfield.bookletserver.controller;

import com.fairfield.bookletserver.SecurityConfiguration;
import com.fairfield.bookletserver.entity.User;
import com.fairfield.bookletserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;

@Controller
public class UserController {
  @Autowired
  UserRepository userRepository;
  @Autowired
  SecurityConfiguration securityConfiguration;

  @GetMapping("/user")
  public String getUser(Model model) {
    model.addAttribute("currentUser", userRepository.getUserFromContext());
    model.addAttribute("headerText", "User Profile");
    model.addAttribute("userList", userRepository.getAllUsers());
    model.addAttribute("user", new User());
    model.addAttribute("user1", userRepository.getUserFromContext());
    return "user";
  }

  @RequestMapping("/register")
  public String getRegistration(Model model,
                                @ModelAttribute User user) {
    if (user.getLevelId() == null) {
      user.setLevelId(1L);
    }
    user.setId(userRepository.getLastId());
    user.setRecentBookletIds(new LinkedList<>());
    userRepository.insertUser(user);
    securityConfiguration.updateUserService(user);

    return getUser(model);
  }

  @RequestMapping("/password")
  public String newPassword(Model model,
                                @ModelAttribute User user1) {
    user1.setUsername(userRepository.getUserFromContext().getUsername());
    userRepository.updatePassword(user1);
    securityConfiguration.updateUserPassword(user1);

    return getUser(model);
  }
}
