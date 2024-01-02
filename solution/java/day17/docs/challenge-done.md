## Day 17: Design one test that has the impact of thousands.

`How could we design one test that has the impact of thousands?`

We can use [`Property-Based Testing`](https://xtrem-tdd.netlify.app/flavours/pbt/). Here we choose to use the
library [`vavr-test`](https://github.com/vavr-io/vavr-test)(you may use alternatives like `junit quickcheck` for
example).

### How to set up property based testing

When we use this technique, we need to think in terms of property.
Here are some `Use Cases` that may inspire you during this kind of reflection :

- Verify **idempotence**
    - `f(f(x)) == f(x)`
    - ex : UpperCase, Create / Delete
- Verify **round-tripping**
    - `from(to(x)) == x`
    - ex : Serialization, PUT / GET, Reverse, Negate
- Check **invariants** (universal properties)
    - `invariant(f(x)) == invariant(x)`
    - ex : Reverse, Map
- Check **commutativity**
    - `f(x, y) == f(y, x)`
    - ex : Addition, Min, Max
- Verify **re-writing / refactoring**
    - `f(x) == new_f(x)`
    - When rewriting, optimizing or refactoring an implementation
- To upgrade **parameterized tests**
    - To replace hardcoded values / discover new test cases (edge cases)

### Property list

- Think about what could go wrong:

-------
for all (invalidInput)
such that convert(invalidInput) is none
-------

- At least one character for a valid input
    - The input itself
    - Or in a given list: "Fizz", "Buzz", "FizzBuzz"

-------
for all (validInput)
such that convert(validInput) is a string looking matching one item from the list: validInput, "Fizz", "Buzz", "
FizzBuzz"
-------

> Be careful when designing properties to not leaking implementation in your test (typically have the modulo logic
> duplicated in a property).

### Write a first property

We use the discoverability of the library to start implementing it.

- We use `Arbitrary` class to generate `invalidInput`

- Our property is green
    - `Never trust a test that have not seen failed`
    - Let's make it fail by not filtering the `Integer` for example

- It fails after 2 runs with value: `21`
    - If we run it again, it will fail for another value
    - `Never rerun a property on failure`: isolate the identified sample in a Unit Test

- We refactor it a little bit (import static, use constants)

- We can now delete the `non passing` test (it is covered by our property)

### Write the "valid" property

- We express this new property
- Let's refactor it to make it simpler to understand

We won't remove the test `parse_successfully_numbers_between_1_and_100_samples`, why?

> **Tip of the day: Property based testing can quickly help your API from wrong inputs**

### Share your experience

How does your code look like?

Please let everyone know in the discord.
