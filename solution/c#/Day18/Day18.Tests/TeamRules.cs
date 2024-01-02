using ArchUnitNET.Fluent.Syntax.Elements.Members.MethodMembers;
using Xunit;
using static ArchUnitNET.Fluent.ArchRuleDefinition;

namespace Day18.Tests
{
    public class TeamRules
    {
        private const string BadNamingUrl =
            "https://yoan-thirion.gitbook.io/knowledge-base/software-craftsmanship/the-programmers-brain#perspective-1-a-good-name-can-be-defined-syntactically";

        private static GivenMethodMembersThat Methods()
            => MethodMembers().That().AreNoConstructors().And();

        [Fact]
        public void No_Get_Method_Should_Return_Void()
            => Methods()
                .HaveName("[gG]et[A-Z].*", useRegularExpressions: true).Should()
                .NotHaveReturnType(typeof(void))
                .Because("any method which gets something should actually return something")
                .Check();

        [Fact]
        public void Iser_And_Haser_Should_Return_Booleans()
            => Methods()
                .HaveName("[iI]s[a-zA-Z]*", useRegularExpressions: true).Or()
                .HaveName("[Hh]as[[A-Za-z].*", useRegularExpressions: true).Should()
                .HaveReturnType(typeof(bool))
                .Because("any method which fetch a state should actually return something (a boolean)")
                .Check();

        [Fact]
        public void Fields_Should_Not_Contain_Consecutive_Underscores()
            => FieldMembers().That()
                .HaveName("__", useRegularExpressions: true)
                .Should()
                .NotExist()
                .Because($"__ ruins readability : {BadNamingUrl}")
                .Check();

        [Fact]
        public void Fields_Should_Not_Start_Or_End_With_Underscore()
            => FieldMembers().That()
                .HaveNameStartingWith("_")
                .Or()
                .HaveNameEndingWith("_")
                .Should()
                .NotExist()
                .Because($"__ ruins readability : {BadNamingUrl}")
                .Check();
    }
}