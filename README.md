# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009652

<h2>Lesson 39. "TABLE JOINs. Practice"</h2>

In this lesson we will create tables, build <b>relationships</b> between them and <b>join</b> them.
Let's start. To prepare we will clear our database:

    DROP TABLE Person


<h3>One to Many</h3>

We will practice on a relationship <b>"Director - their Movies"</b>.

<h4>GENERATED BY DEFAULT AS IDENTITY</h4>

causes creating a sequence named <b>sec_<table-name>_<column-name></b> which will
be used to generate and obtain the next unique value from a sequence to be used as
<b>id</b>entifier or <b>IDENTITY</b>.

<h4>PRIMARY KEY <u>PK</u></h4>

The keyword "<b>PRIMARY KEY</b>" denotes that the column will be used as 
<b>identifying key</b> and therefore also must be UNIQUE and NOT NULL.

<u> Let's create a table <b>Director</b></u>:

    CREATE TABLE Director (
        director_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name varchar(100) NOT NULL UNIQUE,
        age  int CHECK ( age >=3 )

    );

<h4>FOREIGN KEY <u>FK</u></h4>
The <b>PostgreSQL</b> keyword "<b>REFERENCES  <Table-name>(<column-name>)</b>" denotes that the column will refer to a column
with the name <b><column-name></b> in some other table named <b><Table-name></b>. 
And that column  is used out there as a <b>PK</b>.

<u> Let's create a table <b>Director</b></u>:

    CREATE TABLE Movie (
        movie_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        director_id int NOT NULL REFERENCES Director(director_id),
        name varchar(200) NOT NULL,
        year_of_production int NOT NULL CHECK (year_of_production > 1888)    
    );


<u>Result will be</u>:

    first_db@localhost
        first_db
            public
                tables
                    director
                        columns
                            director_id
                            name
                            age
                        keys
                            director_pkey
                            director_name_key
                        indexes    
                            director_name_key
                            director_pkey
                        checks
                            director_age_check
                    movie
                        columns
                            movie_id
                            movie.director_id
                            movie.name
                            year_of_production
                        keys
                            movie_pkey
                        foreign_keys    
                            movie_director_id_fkey
                        indexes
                            movie_pkey
                        checks
                            movie_year_of_production_check
                sequences
                    director_director_id_seq    integer
                    movie_movie_id_seq          integer

Now let's fill the table <b>Director</b> with some data:
        
    INSERT INTO Director(name, age) VALUES('Quentin Tarantino', 59);
    INSERT INTO Director(name, age) VALUES('Martin Scorsese', 80);
    INSERT INTO Director(name, age) VALUES('Guy Ritchie', 54);
    INSERT INTO Director(name, age) VALUES('Woody Allen', 87);
    INSERT INTO Director(name, age) VALUES('David Lynch', 76);
    INSERT INTO Director(name, age) VALUES('Cristopher Nolan', 52);

After execute we can check our table:

    SELECT * FROM Director

<u>Result</u> will be:

    1,Quentin Tarantino,59
    2,Martin Scorsese,80
    3,Guy Ritchie,54
    4,Woody Allen,87
    5,David Lynch,76
    6,Cristopher Nolan,52


Now let's add some movies to the table <b>Movie</b>:

    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(1, 'Reservoir Dogs', 1992);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(1, 'Pulp Fiction', 1994);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(1, 'The Hateful Eight', 2015);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(1, 'Once Upon a Time in Hollywood', 2019);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(2, 'Taxi Driver', 1976);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(2, 'Goodfellas', 1990);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(2, 'The Wolf of Wall Street', 2013);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(3, 'Lock, Stock, and Two Smocking Barrels', 1998);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(3, 'Snatch', 2000);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(4, 'Midight in Paris', 2011);
    INSERT INTO Movie(director_id, name, year_of_production)
        VALUES(6, 'Inception', 2010);

After execute we can check our table:

    SELECT * FROM Movie

<u>Result</u> will be:

    1,1,Reservoir Dogs,1992
    2,1,Pulp Fiction,1994
    3,1,The Hateful Eight,2015
    4,1,Once Upon a Time in Hollywood,2019
    5,2,Taxi Driver,1976
    6,2,Goodfellas,1990
    7,2,The Wolf of Wall Street,2013
    8,3,"Lock, Stock, and Two Smocking Barrels",1998
    9,3,Snatch,2000
    10,4,Midight in Paris,2011
    11,6,Inception,2010


<h4>How also will work REFERENCES</h4>
