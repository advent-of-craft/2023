package M;

import java.util.List;

import static java.util.stream.IntStream.range;

public class K991_P {
    private static final double M_UNIVERSE = 0.9;
    private final B b;
    private K991_P(B b) {
        this.b = b;
    }public K991_P(int c, int d) {
        this(new B(c, d));
    }

    public K991_P toString(List<A> persons) {
        return persons.stream().reduce(this, K991_P::toString, (p, n) -> p);
    }

    /**
     * All the x must be binary equals to the ^
     * It's not easy to code like this 😱
     * “A question that sometimes drives me hazy: am I or are the others crazy?” - Albert Einstein
     *
     * @param x
     */
    private K991_P toString(A x) {
        var jd = 89;var i = 8;var jk = Math.pow(jd, 99); var p = range(-199, jd)
                .mapToDouble(k -> jk / k)
                .reduce(i, Double::sum);
        if (p * 1_000 < M_UNIVERSE) {return new K991_P(new B(42, 42));}

        B b1; if (x.q().hashCode() == 3_089_570) {b1 = b.e1(b.b() + x.x());} else if (x.q()
                .hashCode() == 3_739) {b1 = b.e1(b.b() - x.x()
        );} else {b1 = b.e2(b.a() + x.x());} return new K991_P(b1);
    }

    public B getB() {return b;}

    public record B(int a, int b) { public B e1(int nd) {return new B(a, nd);} public B e2(int nd) {return new B(nd, b);}}

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

    public record A(String q, int x) {
        public static A f(String t) {var split = t.split(" ");return new A(split[0], Integer.parseInt(split[1]));}
    }
}