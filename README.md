# News Management application REST APIs UI.

EPAM java spring mvc rest api curd project on April 15, 2020.<br>
this application is backend for simple News Management application expose REST APIs. + introduce UI layer.<br><br>


### perform the following operations:
- CRUD operations for News<br>
- CRUD operations for Tag.
- CRUD operations for Author.
- Get news. All request parameters are optional and can be used in conjunction.<br>
- Add tag/tags for news message.
- Add news author.
- Each news should have only one author.
- Each news may have more than 1 tag.

### Tools used.
Spring Boot.<br>
JDK version: 8. Use Streams.<br>
Database connection pooling: HikariCP.<br>
Spring JDBC Template.<br>
Build tool: Apache Maven 3.5+. Multi-module project.<br>
Web server: Apache Tomcat.<br>
Application container: Spring IoC. Spring Framework +.<br>
Spring configured via Java config.<br>
Database: PostgresSQL 9.+ or 10.+<br>
Testing: JUnit 4.+ or 5.+, Mockito.<br>
Service layer covered with unit tests.<br>
Repository layer tested using integration tests with an in-memory embedded database.<br>
APIs demonstrated in Postman tool.<br>


### Security
stateless user authentication and verify integrity of JWT token.<br>
OAuth2 as an authorization protocol.<br>
OAuth2 scopes should be used to restrict data.<br>
Implicit grant and Resource owner credentials grant should be implemented.<br>
Implement CSRF protection.<br>
Implement self Signed Certificate protection.<br>

### Register
click on Register then enter your information.<br>
![GitHub](screenshots/register.png)<br>

![GitHub](screenshots/register_page.png)<br><br><br>

### Login
BY GitHub-API<br>
click on git icon and follow the instructions.<br>
![GitHub](screenshots/loginGit.png)<br>

BY User Login<br>
click on Login then enter username and password.<br>
![GitHub](screenshots/login.png)<br>

![GitHub](screenshots/loginUser.png)<br>

### Home Page 
![GitHub](screenshots/home_page.png)<br>

##### Navigation Bar 
![GitHub](screenshots/nav_bar.png)<br>

##### Redirect Home
click home button.<br>
![GitHub](screenshots/home_button.png)<br>

##### Search 
search it can be done by news title or by news author or both of them.<br>
![GitHub](screenshots/search_news.png)<br>

reset search fields, click on reset button.<br>
![GitHub](screenshots/reset_search_news.png)<br>

##### Logout 
click on logout button.<br>
![GitHub](screenshots/logout.png)<br>

##### Goto Add/Edit News
goto Add news click on edit button and keep title and author fields empty.<br>
![GitHub](screenshots/goto_add_news_page_button.png)<br><br><br>

### Add news page
fill all the fields includes tags.<br> 
![GitHub](screenshots/add_news_page.png)<br>

##### Add one or more tag
fill tag name and click "+".<br>
![GitHub](screenshots/add_tag.png)<br>

##### Delete tag
double click into field choose tag which's already has been inserted and click "-".<br>
![GitHub](screenshots/delete_tag.png)<br>

##### Add News
click "+" after filling all fields.<br>
![GitHub](screenshots/add_or_update_news.png)<br>

##### Goto Edit News
first of all select title and author then click edit.<br>
![GitHub](screenshots/goto_edit_news_page_fields.png)<br>

select title.<br>
![GitHub](screenshots/goto_edit_news_page_choosing_title.png)<br>

select author.<br>
![GitHub](screenshots/goto_edit_news_page_choosing_author.png)<br>

click edit button.<br>
![GitHub](screenshots/goto_edit_news_page.png)<br><br><br>

### Edit news page
double click into tag field to see the related tags.<br>
![GitHub](screenshots/edit_news_page.png)<br>

after doing the need it updates, click "+" to update.<br>
![GitHub](screenshots/add_or_update_news.png)<br>

##### Delete news
attention !!! delete allow only for admin users.<br>
![GitHub](screenshots/delete_news.png)<br>

##### Reset Buttons
![GitHub](screenshots/reset_buttons.png)<br>

### iPad Version
![GitHub](screenshots/iPad.png)<br>

### mobile Version
![GitHub](screenshots/mobile.png)<br>




