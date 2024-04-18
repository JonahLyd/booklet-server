package com.fairfield.bookletserver.entity;

import java.util.Date;

public class Booklet {
  private Long id;
  private Long levelId;
  private String fileName;
  private Date created;
  private Date updated;

  private Booklet(Builder builder) {
    setId(builder.id);
    setLevelId(builder.levelId);
    setFileName(builder.fileName);
    setCreated(builder.created);
    setUpdated(builder.updated);
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

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }


  public static final class Builder {
    private Long id;
    private Long levelId;
    private String fileName;
    private Date created;
    private Date updated;

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

    public Builder withFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public Builder withCreated(Date created) {
      this.created = created;
      return this;
    }

    public Builder withUpdated(Date updated) {
      this.updated = updated;
      return this;
    }

    public Booklet build() {
      return new Booklet(this);
    }
  }
}
