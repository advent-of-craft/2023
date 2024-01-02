## Day 22: Design a diamond program using T.D.D and Property-Based Testing.

We think about a possible contract for our class:
- It may contain a `print` method taking the `endCharacter` as parameter
  - It could look like `Character` -> `Option<String>`
  - We could use an `Option` because there are some cases that are not supported (ex: '^', '1', ...)

Let's design and implement it starting from less to more complex properties.

### None for invalid characters

```text
for all (invalidEndCharacter)
such that print(invalidEndCharacter) fail
```

🔴 We start by creating this first property

- We can generate production code from the `property`
- We work on how to generate `invalidEndCharacters`
- We check the generation of random characters
  - Here is an example of values

🟢 We make it pass by `hardcoding` the result

🔵 We clean a little bit the test

### Horizontally symmetric

```text
for all (validEndCharacter)
such that diamond(validEndCharacter) == reverse(diamond(validEndCharacter))
```

🔴 We add a first version of the property

🟢 We make it pass by `hardcoding` the return value

🔵 We extract the guard and use ternary operator

### Is a square  (height = width)

```text
for all (validEndCharacter)
such that diamond(validEndCharacter) is a square
```

The result `String` should a square meaning that each line contains the same number of characters than the number of lines.

🔴 Let's identify if it is a square

🟢 We can make it pass by simply returning 'A'

🔵 We have plenty of duplication in our tests

### Contain 2 identical letters per line

```text
for all (validEndCharacter)
such that each line in diamond(validEndCharacter) contains 2 identical letters except first and last 
```

😬 It is already green...

It is maybe a signal that we need to iterate on the implementation

- We design from the implementation

🔴 Our properties are now failing, we can triangulate the algorithm

- We experiment and learn from the properties
- We fix the property `be_horizontally_symmetric` by iterating on the code
- We fix `contains_2_letters_per_line` by fixing the `toLine` method
- We fix the property `be_a_square` by fixing the `generateEmptyCharacters`

🟢 All our properties are green again 🤩

🔵 Let's refactor our `Diamond` to extract some method and give business names

### Lines have a decreasing number of left spaces

```text
for all (validEndCharacter)
such that Lines have a decreasing number of left white spaces until end character 
```

🟢 Not that easy to create...

🔵 We refactor the test to make it more clear what we do in it

### Lines have a decreasing number of right spaces

🟢 As you may expect the property is green

🔵 We can remove duplications in the test

- We create a new `method` to centralize this logic

> All our properties are green 🤩. Are we confident enough?

### Add an `Approval` test

To increase our confidence we secure our implementation with a `Unit Test`.
We choose to use an `Approval` one.

🔴 It fails because we need to approve the result

🟢 It seems pretty good

We approve the file, and we're done, for now 😉.

Our `Diamond` is complete!

>**Tip of the day: Property based testing with TDD can help you design a more robust implementation.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.
