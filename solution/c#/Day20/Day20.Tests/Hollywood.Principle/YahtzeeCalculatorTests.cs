using Day20.Domain.Yahtzee.Hollywood.Principle;
using FluentAssertions;
using Xunit;
using Xunit.Sdk;

namespace Day20.Tests.Hollywood.Principle
{
    public class YahtzeeCalculatorTests
    {
        public static List<object[]> Numbers() =>
        [
            [DiceBuilder.NewRoll(1, 2, 1, 1, 3), 1, 3],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 6), 1, 0],
            [DiceBuilder.NewRoll(4, 4, 4, 4, 4), 1, 0],
            [DiceBuilder.NewRoll(4, 1, 4, 4, 5), 4, 12]
        ];

        [Theory]
        [MemberData(nameof(Numbers))]
        public void Count_And_Add_Numbers_For_Numbers(DiceBuilder dice, int number, int expectedResult)
            => YahtzeeCalculator.Number(
                dice.Build(),
                number,
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> ThreeOfAKinds() =>
        [
            [DiceBuilder.NewRoll(3, 3, 3, 4, 5), 18],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 6), 0],
            [DiceBuilder.NewRoll(4, 4, 4, 4, 4), 20],
            [DiceBuilder.NewRoll(1, 1, 2, 1, 5), 10]
        ];

        [Theory]
        [MemberData(nameof(ThreeOfAKinds))]
        public void Total_Of_All_Dice_For_Three_Of_A_Kind(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.ThreeOfAKind(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> FourOfAKinds() =>
        [
            [DiceBuilder.NewRoll(3, 3, 3, 3, 5), 17],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 6), 0],
            [DiceBuilder.NewRoll(4, 4, 4, 4, 4), 20],
            [DiceBuilder.NewRoll(1, 1, 1, 1, 5), 9]
        ];

        [Theory]
        [MemberData(nameof(FourOfAKinds))]
        public void Total_Of_All_Dice_For_Four_Of_A_Kind(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.FourOfAKind(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );


        public static List<object[]> FullHouses() =>
        [
            [DiceBuilder.NewRoll(2, 2, 3, 3, 3), 25],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 6), 0],
            [DiceBuilder.NewRoll(4, 4, 1, 4, 1), 25]
        ];

        [Theory]
        [MemberData(nameof(FullHouses))]
        public void Twenty_Five_For_Full_Houses(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.FullHouse(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> SmallStraights() =>
        [
            [DiceBuilder.NewRoll(1, 2, 3, 4, 5), 30],
            [DiceBuilder.NewRoll(5, 4, 3, 2, 1), 30],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 1), 30],
            [DiceBuilder.NewRoll(1, 1, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(SmallStraights))]
        public void Thirty_For_Small_Straights(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.SmallStraight(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> LargeStraights() =>
        [
            [DiceBuilder.NewRoll(1, 2, 3, 4, 5), 40],
            [DiceBuilder.NewRoll(5, 4, 3, 2, 1), 40],
            [DiceBuilder.NewRoll(2, 3, 4, 5, 6), 40],
            [DiceBuilder.NewRoll(1, 4, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(LargeStraights))]
        public void Forty_For_Large_Straights(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.LargeStraight(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> Yahtzees() =>
        [
            [DiceBuilder.NewRoll(4, 4, 4, 4, 4), 50],
            [DiceBuilder.NewRoll(2, 2, 2, 2, 2), 50],
            [DiceBuilder.NewRoll(1, 4, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(Yahtzees))]
        public void Fifty_For_Yahtzees(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.Yahtzee(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public static List<object[]> Chances() =>
        [
            [DiceBuilder.NewRoll(3, 3, 3, 3, 3), 15],
            [DiceBuilder.NewRoll(6, 5, 4, 3, 3), 21],
            [DiceBuilder.NewRoll(1, 4, 1, 3, 2), 11]
        ];

        [Theory]
        [MemberData(nameof(Chances))]
        public void Total_Of_All_Dice_For_Chance(DiceBuilder dice, int expectedResult)
            => YahtzeeCalculator.Chance(
                dice.Build(),
                score => score.Should().Be(expectedResult),
                _ => Fail()
            );

        public class FailFor
        {
            public static List<object[]> InvalidRollLengths() =>
            [
                [1],
                [1, 1],
                [1, 6, 2],
                [1, 6, 2, 5],
                [1, 6, 2, 5, 4, 1],
                [1, 6, 2, 5, 4, 1, 2]
            ];

            [Theory]
            [MemberData(nameof(InvalidRollLengths))]
            public void Invalid_Roll_Lengths(params int[] dice)
            {
                YahtzeeCalculator.Number(dice, 1, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.ThreeOfAKind(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.FourOfAKind(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.FullHouse(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.SmallStraight(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.LargeStraight(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.Yahtzee(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
                YahtzeeCalculator.Chance(dice, _ => Fail(),
                    error => error.Should().Be("Invalid dice... A roll should contain 5 dice."));
            }

            public static List<object[]> InvalidDieInRolls() =>
            [
                [1, 1, 1, 1, 7],
                [0, 1, 1, 1, 2],
                [1, 1, -1, 1, 2]
            ];

            [Theory]
            [MemberData(nameof(InvalidDieInRolls))]
            public void Invalid_Die_In_Rolls(params int[] dice)
            {
                YahtzeeCalculator.Number(dice, 1, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.ThreeOfAKind(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.FourOfAKind(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.FullHouse(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.SmallStraight(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.LargeStraight(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.Yahtzee(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
                YahtzeeCalculator.Chance(dice, _ => Fail(),
                    error => error.Should().Be("Invalid die value. Each die must be between 1 and 6."));
            }
        }

        private static void Fail() => throw new XunitException("Should not go through this branch...");
    }
}