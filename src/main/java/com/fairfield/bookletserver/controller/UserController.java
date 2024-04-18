package com.fairfield.bookletserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Component
@RestController
public class UserController {

  @GetMapping("/user")
  public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal, Model model) {
    return Collections.singletonMap("name", principal.getAttribute("name"));
  }
}
