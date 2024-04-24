package com.fairfield.bookletserver.repository;

import com.fairfield.bookletserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserRepository {
  Map<String, User> nameToUser;

  @Autowired
  public void init() {
    nameToUser = new HashMap<>();
    Queue<Long> stockRecents = new LinkedList<>();
    stockRecents.add(1L);
    stockRecents.add(2L);
    stockRecents.add(3L);
    stockRecents.add(4L);
    Queue<Long> stockRecents2 = new LinkedList<>();
    stockRecents2.add(1L);
    stockRecents2.add(2L);
    stockRecents2.add(3L);
    stockRecents2.add(4L);

    var admin1 = User.newBuilder()
        .withId(1L)
        .withLevelId(2L)
        .withFirstName("Jonah")
        .withLastName("Lydon")
        .withUsername("jonah.lydon@student.fairfield.edu")
        .withPassword("pwd")
        .withPasswordConf("pwd")
        .withRecentBooklets(stockRecents)
        .build();
    var user1 = User.newBuilder()
        .withId(2L)
        .withLevelId(2L)
        .withFirstName("Habibul")
        .withLastName("Huq")
        .withUsername("habibul.huq@student.fairfield.edu")
        .withPassword("pwd")
        .withPasswordConf("pwd")
        .withRecentBooklets(stockRecents2)
        .build();
    var user2 = User.newBuilder()
        .withId(3L)
        .withLevelId(1L)
        .withFirstName("Mateo")
        .withLastName("Davalos")
        .withUsername("mateo.davalos@student.fairfield.edu")
        .withPassword("pwd")
        .withPasswordConf("pwd")
        .withRecentBooklets(new LinkedList<>())
        .build();

    insertUser(admin1);
    insertUser(user1);
    insertUser(user2);
  }

  public void insertUser(User user) {
    nameToUser.put(user.getUsername(), user);
  }

  public void updateRecentBooklet(Long bookletId) {
    var user = getUserFromContext();
    var books = user.getRecentBookletIds();
    if (!books.contains(bookletId)) {
      if (books.size() == 4) {
        books.remove();
      }
      books.add(bookletId);
      user.setRecentBookletIds(books);
    }
  }

  public User getUserFromContext() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    var username = "";
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      username =  auth.getName();
    }
    return nameToUser.get(username);
  }

  public User getUserByAuth(String auth) {
    var username = auth.split(":")[0];
    var user = nameToUser.get(username);

    if (user != null && user.getCredentials().equals(auth)) {
      return user;
    } else {
      return null;
    }
  }

  public Collection<Long> getRecentBookletIds() {
    return getUserFromContext().getRecentBookletIds();
  }
}
