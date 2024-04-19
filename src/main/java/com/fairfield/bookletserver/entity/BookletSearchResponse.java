package com.fairfield.bookletserver.entity;

public class BookletSearchResponse {
  private String fileName;
  private String pathToFile;

  public BookletSearchResponse() {

  }

  private BookletSearchResponse(Builder builder) {
    setFileName(builder.fileName);
    setPathToFile(builder.pathToFile);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getPathToFile() {
    return pathToFile;
  }

  public void setPathToFile(String pathToFile) {
    this.pathToFile = pathToFile;
  }


  public static final class Builder {
    private String fileName;
    private String pathToFile;

    private Builder() {
    }

    public Builder withFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public Builder withPathToFile(String pathToFile) {
      this.pathToFile = pathToFile;
      return this;
    }

    public BookletSearchResponse build() {
      return new BookletSearchResponse(this);
    }
  }
}
