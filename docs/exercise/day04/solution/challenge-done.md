## Day 4: Identify the behavior under test and rewrite the tests.

### The issue with Unit testing

It does not make any sense to write 1 test for each data changed 
by the call of a behavior (`method` call).

- Let's go back to the business and identify the behavior under test
  - We can use a test list to do so

- We can also add a new test case in our test list we haven't covered

```markdown
- Add a comment (non existing) in an article
- Add a comment (non existing) in an article containing already a comment 
✅ Fail when adding an existing comment in an article
```

- From this list:
  - We should cover those 3 behaviors
  - We can iterate on the existing tests
  - We update our test list as we cover them
  - We cover all the test cases

### Optimisations

- We still have duplications... let's improve our tests
  - By creating constants and creating the field `article`

- One proposal we can make is to organise our tests by `passing` and `non passing` tests
  - We can do that with `Nested` classes

>**Tip of the day: your test code is as important as your production code.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.
