using Day20.Domain.Yahtzee.Constrain.Input;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using Xunit;
using static Day20.Tests.Constrain.Input.RollBuilder;

namespace Day20.Tests.Constrain.Input
{
    public class YahtzeeCalculatorTests
    {
        public static List<object[]> Numbers() =>
        [
            [NewRoll(1, 2, 1, 1, 3), 1, 3],
            [NewRoll(2, 3, 4, 5, 6), 1, 0],
            [NewRoll(4, 4, 4, 4, 4), 1, 0],
            [NewRoll(4, 1, 4, 4, 5), 4, 12]
        ];

        [Theory]
        [MemberData(nameof(Numbers))]
        public void Count_And_Add_Numbers_For_Numbers(RollBuilder roll, int number, int expectedResult)
            => YahtzeeCalculator.Number(roll.Build(), number).Should().Be(expectedResult);

        public static List<object[]> ThreeOfAKinds() =>
        [
            [NewRoll(3, 3, 3, 4, 5), 18],
            [NewRoll(2, 3, 4, 5, 6), 0],
            [NewRoll(4, 4, 4, 4, 4), 20],
            [NewRoll(1, 1, 2, 1, 5), 10]
        ];

        [Theory]
        [MemberData(nameof(ThreeOfAKinds))]
        public void Total_Of_All_Dice_For_Three_Of_A_Kind(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.ThreeOfAKind(roll.Build()).Should().Be(expectedResult);

        public static List<object[]> FourOfAKinds() =>
        [
            [NewRoll(3, 3, 3, 3, 5), 17],
            [NewRoll(2, 3, 4, 5, 6), 0],
            [NewRoll(4, 4, 4, 4, 4), 20],
            [NewRoll(1, 1, 1, 1, 5), 9]
        ];

        [Theory]
        [MemberData(nameof(FourOfAKinds))]
        public void Total_Of_All_Dice_For_Four_Of_A_Kind(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.FourOfAKind(roll.Build()).Should().Be(expectedResult);


        public static List<object[]> FullHouses() =>
        [
            [NewRoll(2, 2, 3, 3, 3), 25],
            [NewRoll(2, 3, 4, 5, 6), 0],
            [NewRoll(4, 4, 1, 4, 1), 25]
        ];

        [Theory]
        [MemberData(nameof(FullHouses))]
        public void Twenty_Five_For_Full_Houses(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.FullHouse(roll.Build()).Should().Be(expectedResult);

        public static List<object[]> SmallStraights() =>
        [
            [NewRoll(1, 2, 3, 4, 5), 30],
            [NewRoll(5, 4, 3, 2, 1), 30],
            [NewRoll(2, 3, 4, 5, 1), 30],
            [NewRoll(1, 1, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(SmallStraights))]
        public void Thirty_For_Small_Straights(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.SmallStraight(roll.Build()).Should().Be(expectedResult);

        public static List<object[]> LargeStraights() =>
        [
            [NewRoll(1, 2, 3, 4, 5), 40],
            [NewRoll(5, 4, 3, 2, 1), 40],
            [NewRoll(2, 3, 4, 5, 6), 40],
            [NewRoll(1, 4, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(LargeStraights))]
        public void Forty_For_Large_Straights(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.LargeStraight(roll.Build()).Should().Be(expectedResult);

        public static List<object[]> Yahtzees() =>
        [
            [NewRoll(4, 4, 4, 4, 4), 50],
            [NewRoll(2, 2, 2, 2, 2), 50],
            [NewRoll(1, 4, 1, 3, 2), 0]
        ];

        [Theory]
        [MemberData(nameof(Yahtzees))]
        public void Fifty_For_Yahtzees(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.Yahtzee(roll.Build()).Should().Be(expectedResult);

        public static List<object[]> Chances() =>
        [
            [NewRoll(3, 3, 3, 3, 3), 15],
            [NewRoll(6, 5, 4, 3, 3), 21],
            [NewRoll(1, 4, 1, 3, 2), 11]
        ];

        [Theory]
        [MemberData(nameof(Chances))]
        public void Total_Of_All_Dice_For_Chance(RollBuilder roll, int expectedResult)
            => YahtzeeCalculator.Chance(roll.Build()).Should().Be(expectedResult);

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
                => Roll.Parse(dice)
                    .Should()
                    .BeLeft(error =>
                        error.Should().Be(new ParsingError("Invalid dice... A roll should contain 5 dice."))
                    );

            public static List<object[]> InvalidDieInRolls() =>
            [
                [1, 1, 1, 1, 7],
                [0, 1, 1, 1, 2],
                [1, 1, -1, 1, 2]
            ];

            [Theory]
            [MemberData(nameof(InvalidDieInRolls))]
            public void Invalid_Die_In_Rolls(params int[] dice)
                => Roll.Parse(dice)
                    .Should()
                    .BeLeft(error =>
                        error.Should().Be(new ParsingError("Invalid die value. Each die must be between 1 and 6."))
                    );
        }
    }
}