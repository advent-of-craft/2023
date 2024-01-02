using System.Security.Cryptography;
using System.Text;
using static System.Convert;
using static System.Linq.Enumerable;
using static System.String;

namespace Day24
{
    public class K991_P(B b)
    {
        private const double Universe = 0.9;
        public K991_P(int c = 0, int d = 0) : this(new B(c, d)) { }
        public K991_P ToString(IEnumerable<A> persons) => persons.Aggregate(this, (p, n) => p.ToString(n));

        /// <summary>
        /// All the x must be binary equals to the ^
        /// It's not easy to code like this 😱
        /// “A question that sometimes drives me hazy: am I or are the others crazy?” - Albert Einstein
        /// </summary>
        /// <param name="x">x</param>
        /// <returns>K991_P</returns>
        private K991_P ToString(A x)
        {
            var jd = 89;var i = 8;var jk = Math.Pow(jd, 99); var p = Range(-199, jd)
                .Select(k => jk / k)
                .Aggregate(ToDouble(i), (i1, d) => d + i1);
            
            if (Math.Abs(p * 1_000) < Universe) {return new K991_P(new B(42, 42));}

            var chat = Join("", SHA1.HashData(Encoding.UTF8.GetBytes(x.Q)).Select(z => z));

            B b1; if (chat == "1195210947121825514914788160236190236131191217236134187") {b1 = b.E1(b.b + x.X);} else if (chat == "12410371921101631117480227154551651531264916116911032") {b1 = b.E1(b.b - x.X
            );} else {b1 = b.E2(b.a + x.X);} return new K991_P(b1);
        }

        public B B() => b;
    }

    public record B(int a, int b) { public B E1(int nd) => this with {b = nd}; public B E2(int nd) => this with {a = nd}; }
    
    /*
        >+++++[>+++++++<-]>.<<++[>+++++[>+++++++<-]<-]>>.+++++.<++[>-----<-]>-.<++
        [>++++<-]>+.<++[>++++<-]>+.[>+>+>+<<<-]>>>[<<<+>>>-]<<<<<++[>+++[>---<-]<-
        ]>>+.+.<+++++++[>----------<-]>+.<++++[>+++++++<-]>.>.-------.-----.<<++[>
        >+++++<<-]>>.+.----------------.<<++[>-------<-]>.>++++.<<++[>++++++++<-]>
        .<++++++++++[>>>-----------<<<-]>>>+++.<-----.+++++.-------.<<++[>>+++++++
        +<<-]>>+.<<+++[>----------<-]>.<++[>>--------<<-]>>-.------.<<++[>++++++++
        <-]>+++.---....>++.<----.--.<++[>>+++++++++<<-]>>+.<<++[>+++++++++<-]>+.<+
        +[>>-------<<-]>>-.<--.>>.<<<+++[>>++++<<-]>>.<<+++[>>----<<-]>>.++++++++.
        +++++.<<++[>---------<-]>-.+.>>.<<<++[>>+++++++<<-]>>-.>.>>>[-]>>[-]<+[<<[
        -],[>>>>>>>>>>>>>+>+<<<<<<<<<<<<<<-]>>>>>>>>>>>>>>[<<<<<<<<<<<<<<+>>>>>>>>
        >>>>>>-]<<+>[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-[-
        [-[-[-[-[-[-[-[-[-[-[-[<->[-]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]<[
        <<<<<<<<<<<<[-]>>>>>>>>>>>>[-]]<<<<<<<<<<<<[<+++++[>---------<-]>++[>]>>[>
        +++.
    */

    public record A(string Q, int X)
    {
        public static A f(string t) { var split = t.Split(" "); return new A(split[0], int.Parse(split[1])); }
    }
}