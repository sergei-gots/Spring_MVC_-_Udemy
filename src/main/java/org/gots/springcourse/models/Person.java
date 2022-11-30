package org.gots.springcourse.models;


import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Full name should not be empty")
    @Size(min = 2, max = 100, message = "Full name should have between 2 and 100 characters")
    private String full_name;
    @Min(value=1900, message = "Year of birth should be greater than or equal to 1900")
    @Max(value=2023, message = "Year of birth should be less than or equal to 2023")
    private int year_of_birth;
    @NotEmpty(message = "E-mail should not be empty")
    @Size(max=32, message = "EMail shouldn't be longer than 32 symbols")
    @Email(message = "E-mail should be valid")
    private String email;

    public Person() {}
    public Person(int id, String full_name, int year_of_birth, String email) {
        this.id = id;
        this.full_name = full_name;
        System.out.println("Person.fullname is " + this.full_name);
        this.year_of_birth = year_of_birth;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name()         {   return full_name;    }

    public void setFull_name(String full_name) {   this.full_name = full_name;   }

    public int getYear_of_birth()              {   return year_of_birth;         }

    public void setYear_of_birth(int year_of_birth)      {   this.year_of_birth = year_of_birth;    }

    public String getEmail()         {  return email;    }
    public void setEmail(String email) {   this.email = email;    }

}
