## Day 18: Automatically detect Linguistic Anti-Patterns (LAP).

`What's the problem with this code?` (except the name's class 😁)

This kind of issues in the code are [`Linguistic Anti Patterns`](https://www.veneraarnaoudova.ca/linguistic-anti-pattern-detector-lapd/las/) as named by [Venera Arnaoudova](https://www.veneraarnaoudova.ca/).

> How could we simply detect these issues?

### Architecture Unit Tests
Take a look at [this page](https://xtrem-tdd.netlify.app/Flavours/archunit) describing this concept.

- We can use the library [`archunit`](https://www.archunit.org/) in java to describe and enforce architecture / design rules.

- We can now create a test class to materialize this kind of guideline
  - We use the `@AnalyzeClasses` annotation and configure which packages need to be analyzed

```java
@AnalyzeClasses(packages = "lap", importOptions = ImportOption.DoNotIncludeTests.class)
public class TeamRules {
}
```

- Even if we are not expert in this library, its discoverability makes it really easy to use and learn
  - Read more about [`Dot Driven Development`](https://blog.ploeh.dk/2012/05/25/Designpatternsacrossparadigms/#ebe4a8c5ba664c6fb5ea07c8b7e18555)

#### No getter can return `void`
- We write a skeleton of this rule
- We generate the `notBeVoid` method
- Once done, we run the tests
  - The anti-pattern is detected

- We can check that it works correctly by fixing the code and run the tests again

```java
public int getData() {
    return 42;
}
```

- The test is green, our LA detection is working like a charm

#### Iser / haser must return a `boolean`
- We work on `is` detection now

#### Let's detect bad names
- Based on Simon Butler's work, we can try to detect bad naming in our code
  - The ones which increase drastically our cognitive load while reading code...

- We can write 2 rules to detect `underscore anomalies`
- We refactor our `rules` for centralizing the logic on the field
  - We extract a `method` then the `parameters`
  - Our IDE is smart and make the replacement in the other rule for us 🤩

#### Our Team Rules
Here are the `rules` we end up with:
- no_getter_can_return_void
- iser_haser_must_return_boolean
- detect_consecutives_underscores
- detect_external_underscores

>**Tip of the day: Proactively testing your architecture will avoid design problems.**

### Share your experience

How this technique could be useful for you?

What rules would you need?

Please let everyone know in the discord.
