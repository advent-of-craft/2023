## Day 4: Identify the behavior under test and rewrite the tests.

In `Unit Test`, Unit does not stand for data...

```java
@Test
void it_should_add_valid_comment() throws CommentAlreadyExistException {
    // Arrange
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );
    
    // Act
    article.addComment("Amazing article !!!", "Pablo Escobar");
    
    // Assert
    // Where the Fuck are the assertions 😬
}

@Test
void it_should_add_a_comment_with_the_given_text() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    var text = "Amazing article !!!";
    article.addComment(text, "Pablo Escobar");

    assertThat(article.getComments())
            .hasSize(1)
            .anyMatch(comment -> comment.text().equals(text));
}

@Test
void it_should_add_a_comment_with_the_given_author() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    var author = "Pablo Escobar";
    article.addComment("Amazing article !!!", author);

    assertThat(article.getComments())
            .hasSize(1)
            .anyMatch(comment -> comment.author().equals(author));
}
...
```

It does not make any sense to write 1 test for each data changed by the call of a behavior (`method` call).

- Let's go back to the business and identify the behavior under test
  - We can use a test list to do so

```markdown
- Add a comment (non existing) in an article
✅ Fail when adding an existing comment in an article
```

🤔By clarifying which behaviors are supported, we may identify a non tested one : `What happens when we add a comment in an article that already contains another comment?`

- We add this new test case in our test list

```markdown
- Add a comment (non existing) in an article
- Add a comment (non existing) in an article containing already a comment 
✅ Fail when adding an existing comment in an article
```

- We should cover those 3 behaviors
  - We can iterate on the existing tests

```java
@Test
void it_should_add_comment_in_an_article() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    article.addComment("Amazing article !!!", "Pablo Escobar");

    assertThat(article.getComments()).hasSize(1);
    var comment = article.getComments().get(0);

    assertThat(comment.text()).isEqualTo("Amazing article !!!");
    assertThat(comment.author()).isEqualTo("Pablo Escobar");
}
```

- We update our test list

```markdown
✅ Add a comment (non existing) in an article
- Add a comment (non existing) in an article containing already a comment 
✅ Fail when adding an existing comment in an article
```

- We create the third test

```java
 @Test
void it_should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    article.addComment("Amazing article !!!", "Pablo Escobar");
    article.addComment("Finibus Bonorum et Malorum", "Al Capone");

    assertThat(article.getComments()).hasSize(2);

    var lastComment = article.getComments().getLast();
    assertThat(lastComment.text()).isEqualTo("Finibus Bonorum et Malorum");
    assertThat(lastComment.author()).isEqualTo("Al Capone");
}
```

```markdown
✅ Add a comment (non existing) in an article
✅ Add a comment (non existing) in an article containing already a comment 
✅ Fail when adding an existing comment in an article
```

- Congratulations, we have fulfilled the test list

```java
@Test
void it_should_add_comment_in_an_article() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    article.addComment("Amazing article !!!", "Pablo Escobar");

    assertThat(article.getComments()).hasSize(1);
    var comment = article.getComments().get(0);

    assertThat(comment.text()).isEqualTo("Amazing article !!!");
    assertThat(comment.author()).isEqualTo("Pablo Escobar");
}

@Test
void it_should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    article.addComment("Amazing article !!!", "Pablo Escobar");
    article.addComment("Finibus Bonorum et Malorum", "Al Capone");

    assertThat(article.getComments()).hasSize(2);

    var lastComment = article.getComments().getLast();
    assertThat(lastComment.text()).isEqualTo("Finibus Bonorum et Malorum");
    assertThat(lastComment.author()).isEqualTo("Al Capone");
}

@Test
void it_should_throw_an_exception_when_adding_existing_comment() {
    var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );

    assertThatThrownBy(() -> {
        article.addComment("Amazing article !!!", "Pablo Escobar");
        article.addComment("Amazing article !!!", "Pablo Escobar");
    }).isInstanceOf(CommentAlreadyExistException.class);
}
```

- We still have duplications... let's improve our tests
  - By creating constants and creating the field `article`

```java
class ArticleTests {
  public static final String AUTHOR = "Pablo Escobar";
  private static final String COMMENT_TEXT = "Amazing article !!!";
  private Article article;

  @BeforeEach
  void setup() {
    article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    );
  }

  @Test
  void it_should_add_comment_in_an_article() throws CommentAlreadyExistException {
    article.addComment(COMMENT_TEXT, AUTHOR);

    assertThat(article.getComments()).hasSize(1);

    var comment = article.getComments().get(0);
    assertThat(comment.text()).isEqualTo(COMMENT_TEXT);
    assertThat(comment.author()).isEqualTo(AUTHOR);
  }

  @Test
  void it_should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
    var newComment = "Finibus Bonorum et Malorum";
    var newAuthor = "Al Capone";

    article.addComment(COMMENT_TEXT, AUTHOR);
    article.addComment(newComment, newAuthor);

    assertThat(article.getComments()).hasSize(2);

    var lastComment = article.getComments().getLast();
    assertThat(lastComment.text()).isEqualTo(newComment);
    assertThat(lastComment.author()).isEqualTo(newAuthor);
  }

  @Test
  void it_should_throw_an_exception_when_adding_existing_comment() throws CommentAlreadyExistException {
    article.addComment(COMMENT_TEXT, AUTHOR);

    assertThatThrownBy(() -> {
      article.addComment(COMMENT_TEXT, AUTHOR);
    }).isInstanceOf(CommentAlreadyExistException.class);
  }
}
```

- One proposal we can make is to organise our tests by `passing` and `non passing` tests
  - We can do that with `Nested` classes

```java
class ArticleTests {
    public static final String AUTHOR = "Pablo Escobar";
    private static final String COMMENT_TEXT = "Amazing article !!!";
    private Article article;

    @BeforeEach
    void setup() {
        article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        article.addComment(COMMENT_TEXT, AUTHOR);

        assertThat(article.getComments()).hasSize(1);

        var comment = article.getComments().get(0);
        assertThat(comment.text()).isEqualTo(COMMENT_TEXT);
        assertThat(comment.author()).isEqualTo(AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        var newComment = "Finibus Bonorum et Malorum";
        var newAuthor = "Al Capone";

        article.addComment(COMMENT_TEXT, AUTHOR);
        article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(2);

        var lastComment = article.getComments().getLast();
        assertThat(lastComment.text()).isEqualTo(newComment);
        assertThat(lastComment.author()).isEqualTo(newAuthor);
    }

    @Nested
    class Fail {
        @Test
        void when__adding_an_existing_comment() throws CommentAlreadyExistException {
            article.addComment(COMMENT_TEXT, AUTHOR);

            assertThatThrownBy(() -> {
                article.addComment(COMMENT_TEXT, AUTHOR);
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }
}
```