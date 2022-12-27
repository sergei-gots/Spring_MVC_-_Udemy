package org.gots.springcourse.models;


import javax.validation.constraints.*;

public class Book {

    private int id;

   @NotEmpty(message = "Name of book should not be empty")
   @Size(min = 2, max = 100, message = "Name of book should have between 2 and 100 characters")
    private String title;

    @NotEmpty(message = "Name of author should not be empty")
    @Size(min = 2, max = 100, message = "Name of author should have between 2 and 100 characters")
    private String author;

    @Min(value=1900, message = "Year of birth should be greater than or equal to 1900")
    @Max(value=2023, message = "Year of birth should be less than or equal to 2023")

//    @Min(value=-10000, message = "Year should be greater than -10000 and 2050")
  //  @Max(value=2050, message = "Year should be less than 2050")
    private int year;

    private int person_id;


    public Book() {}

    public Book(int id, String title, String author, int year, int person_id) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle()         {   return title;    }

    public void setTitle(String title) {  this.title = title;   }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
