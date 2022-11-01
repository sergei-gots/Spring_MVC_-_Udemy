# Spring_MVC_-_Udemy_course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/share/106mco3@zXXjh7_J3tEIhJSLaZq8LJmPgwzJIv2CQK-BErnNfijiCKpZTjsgYb9WffxDjCo0Vg==/

<h2>Lesson 20. "GET-request parameters. Annotation @RequestParam"</h2>

- <b>URL</b> - <b>U</b>niform <b>R</b>esource <b>L</b>ocator
  - <b>GET</b>-request: All the parameteres are located in the <b>URL</b>.<br> access via:
    - Java <b><u>Class</u></b>: <b>HttpServletRequest</b>: this way gives access to all the
      existing data of a request:
        - method: <b>getParameter(String s)</b>
    <br>
    - Java <u>method-<b>parameter Annotation</b></u>: <b>@RequestParam(value = "value"</b>):
    this way is more compact and comfortable to read.
<br>
<br>
- <u>NOTE:</u> When we use parameter-annotations <b>@RequestParam</b> to access request parameters and 
  on of the annotated parameters is missing in the HTTP-request
  the Error 404 will be appeared.<br>
  To avoid this we can use the field <b>required=false</b>
  