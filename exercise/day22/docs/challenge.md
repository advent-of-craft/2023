## Day 22: Activate the diamond.

Your journey has been tumultuous so far. The last storm was too strong.
You need to protect your ship for your last trip back home.

You will need to activate a protection diamond with the TDD magic to protect yourself
from future storms.

Today's exercise is about designing a `diamond` program.

The program will take a parameter a letter indicating the depth of the diamond.

supplying C as parameter will display
```java
  A
 B B
C   C
 B B
  A
```

To activate the diamond fully, you will need to approach it using `Property-Based` Testing.

> **Challenge of day 22: Design a diamond program using T.D.D and Property-Based Testing.**

- <u>💡HINT:</u> Start by listing out the simple properties.

> You can combine the three magics: TDD / PBT and TCR for better results.

### Use `Test && Commit || Revert` (TCR)
You can first read [`TCR explanations`](https://medium.com/@kentbeck_7670/test-commit-revert-870bbd756864) from Kent Beck.

Then you can take look at the [`Murex` tool documentation](https://github.com/murex/TCR) that we propose to use to apply this development cycle in this challenge.

Basically to use it, during this challenge you have to run the below command in the `day22` directory:
```shell
./tcrw
```

![Diamond kata](snippet.png)
