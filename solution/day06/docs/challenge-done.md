## Day 6: Parameterize your tests.

### How to parameterize your test

`Parameterized tests` can save us a lot of time. 
Know more about this concept in java [here](https://www.baeldung.com/parameterized-tests-junit-5).

- We start by adding the dependency on `junit-jupiter-params`

- We adapt the first test
  - By extracting parameters

- Then, we define a method source
- We set the values for this test
- The test should be green...

- Let's make it fail for a good reason to be sure we can trust it
  - We change the `expectedResult` for that

- We are pretty confident, so let's move other tests in `validInputs`

### Advices on tests

- With `Parameterized Tests` it could be tempting to write this kind of test
  - Here, add edge cases inside the test...

- One advice for you, try to avoid to take decisions in your tests
  - Decisions mean you have different test cases...
  - Have dedicated test methods for specific test cases

>**Tip of the day: Don't trust Tests. Unless you have seen they fail first.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.
