package com.fairfield.bookletserver.repository;

import com.fairfield.bookletserver.entity.Booklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class BookletRepository {
  private Map<Long, Booklet> idToBooklet;
  private Map<String, Booklet> nameToBooklet;
  private Stack<Booklet> recents;

  @Autowired
  public void init() {
    recents = new Stack<>();
    idToBooklet = new HashMap<>();
    nameToBooklet = new HashMap<>();
    Booklet book1 = Booklet.newBuilder()
        .withId(1L)
        .withLevelId(1L)
        .withFileName("silverSA_TDSDec2016.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    Booklet book2 = Booklet.newBuilder()
        .withId(2L)
        .withLevelId(1L)
        .withFileName("TDS_vinyl_coat.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    Booklet book3 = Booklet.newBuilder()
        .withId(3L)
        .withLevelId(2L)
        .withFileName("brAlloy25C17200StripDataSheet.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    Booklet book4 = Booklet.newBuilder()
        .withId(4L)
        .withLevelId(2L)
        .withFileName("hard-chrome-105-plating-proces.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    Booklet book5 = Booklet.newBuilder()
        .withId(5L)
        .withLevelId(2L)
        .withFileName("jotunSurface37f.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    Booklet book6 = Booklet.newBuilder()
        .withId(6L)
        .withLevelId(2L)
        .withFileName("SDS_-_Bright_Gold_Solution_-2022.pdf")
        .withCreated(Date.valueOf("2024-04-18"))
        .withUpdated(Date.valueOf("2024-04-18"))
        .build();
    idToBooklet.put(1L,book1);
    idToBooklet.put(2L, book2);
    idToBooklet.put(3L, book3);
    idToBooklet.put(4L, book4);
    idToBooklet.put(5L, book5);
    idToBooklet.put(6L, book6);
    nameToBooklet.put(book1.getFileName(),book1);
    nameToBooklet.put(book2.getFileName(), book2);
    nameToBooklet.put(book3.getFileName(), book3);
    nameToBooklet.put(book4.getFileName(), book4);
    nameToBooklet.put(book5.getFileName(), book5);
    nameToBooklet.put(book6.getFileName(), book6);
    recents.push(book1);
    recents.push(book2);
    recents.push(book5);
    recents.push(book4);
  }

  public void insertBooklet(Booklet booklet) {
    idToBooklet.put(booklet.getId(), booklet);
  }

  public Booklet getBookletById(Long id) {
    recents.pop();
    recents.push(idToBooklet.get(id));
    return idToBooklet.get(id);
  }

  public Booklet getBookletByName(String fileName) {
    recents.pop();
    recents.push(nameToBooklet.get(fileName));
    return nameToBooklet.get(fileName);
  }

  public String getBookletFileNameById(Long id) {
    recents.pop();
    recents.push(idToBooklet.get(id));
    return idToBooklet.get(id).getFileName();
  }

  public List<Booklet> getRecentBooklets() {
    return recents.stream().toList();
  }
}
