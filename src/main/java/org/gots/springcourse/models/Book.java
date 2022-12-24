package org.gots.springcourse.models;


import javax.validation.constraints.*;

public class Book {

    private int book_id;

   @NotEmpty(message = "Name of book should not be empty")
   @Size(min = 2, max = 100, message = "Name of book should have between 2 and 100 characters")
    private String name;

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

    public Book(int book_id, String name, String author, int year, int person_id) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName()         {   return name;    }

    public void setName(String name) {  this.name = name;   }

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

    public boolean isAvailable() { return person_id == 0; }
}
