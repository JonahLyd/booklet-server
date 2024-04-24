package com.fairfield.bookletserver.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Queue;

public class User implements Authentication {
  private Long id;
  private Long levelId;
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String passwordConf;
  private boolean isAuthenticated;
  private Queue<Long> recentBookletIds;

  private User(Builder builder) {
    setId(builder.id);
    setLevelId(builder.levelId);
    setUsername(builder.username);
    setFirstName(builder.firstName);
    setLastName(builder.lastName);
    setPassword(builder.password);
    setPasswordConf(builder.passwordConf);
    setRecentBookletIds(builder.recentBookletIds);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getLevelId() {
    return levelId;
  }

  public void setLevelId(Long levelId) {
    this.levelId = levelId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConf() {
    return passwordConf;
  }

  public void setPasswordConf(String passwordConf) {
    this.passwordConf = passwordConf;
  }

  public Queue<Long> getRecentBookletIds() {
    return recentBookletIds;
  }

  public void setRecentBookletIds(Queue<Long> recentBookletIds) {
    this.recentBookletIds = recentBookletIds;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return this.username + ":" + this.password;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return null;
  }


  public static final class Builder {
    private Long id;
    private Long levelId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String passwordConf;
    private Queue<Long> recentBookletIds;

    private Builder() {
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withLevelId(Long levelId) {
      this.levelId = levelId;
      return this;
    }

    public Builder withUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withPasswordConf(String passwordConf) {
      this.passwordConf = passwordConf;
      return this;
    }

    public Builder withRecentBooklets(Queue<Long> recentBookletIds) {
      this.recentBookletIds = recentBookletIds;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
