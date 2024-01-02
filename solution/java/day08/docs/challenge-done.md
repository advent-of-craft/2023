## Day 8: Using TDD rules, write a password validation program.

First step when using T.D.D, prepare a test list from the `business rules` and add examples for each.
Here are the business rules :

```text
- Contains at least 8 characters
- Contains at least one capital letter
- Contains at least one lowercase letter
- Contains at least a number
- Contains at least a special character in this list `. * # @ $ % &`.
- Any other characters are not authorized.
```

We can do it using [`Example Mapping`](https://xtrem-tdd.netlify.app/Flavours/example-mapping) workshop.

Here are some examples we can use for this business rule:

```text
- Empty string
- "a"
- "xxxxxxx" - edge case
```

🔴 Compile error

We design the method from our test.

🟢 Then we [generate the code through our IDE](https://xtrem-tdd.netlify.app/Flavours/generate-code-from-usage)

🔵 We can move the generated class to a named package (`password`)

🔴🟢= 🟤 We add another `failing` test... is it failing?

> Why our test is not red?

If our test is green all the way through, that is:

- Poorly designed tests
- Poorly tested behaviour
- Already supported / implemented feature

🔵 Before tackling the problem, let's refactor our tests.

- We update our test list
    - And add the last one

- Add passing Unit Test

🔴 Let's add a passing test to our suite
🟢 Iterate on the implementation
🔵 Let's clean up the production code

- Simplify `conditional` and add a private constructor (Utility class)
- We refactor the tests as well by splitting them into `passing` and `non-passing`
    - We create a `Nested` fail class

- We do it for all the business rules
- We iterate on the `Tests List`
- Then we use `Parameterized tests` and `simplify` our code as well

> How many times did you debug your code during this day? 🤔

- From the beginning, we have chosen to not use `Regex` but we can easily solve this challenge with only one line of
  code...
    - Ask ChatGPT to generate it for you 😉