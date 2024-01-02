package M

import java.lang.Double.sum
import java.util.stream.IntStream.range
import kotlin.math.pow

class K991_P(val b: B) {
    private val M_UNIVERSE = 0.9

    fun toString(persons: List<A>): K991_P = persons.fold(this) { p, n -> p.toString(n) }

    /**
     * All the x must be binary equals to the ^
     * It's not easy to code like this 😱
     * “A question that sometimes drives me hazy: am I or are the others crazy?” - Albert Einstein
     *
     * @param x
     */
    private fun toString(x: A): K991_P {
        val jd = 89.0
        val i = 8

        val jk: Double = jd.pow(99.0)

        val p = range(-199, jd.toInt()).mapToDouble { k: Int -> jk / k }.reduce(i.toDouble()) { a: Double, b: Double -> sum(a, b) }
        if (p * 1000 < M_UNIVERSE) {
            return K991_P(B(42, 42))
        }

        val b1 = if (x.q.hashCode() == 3089570) { b.e1(b.b + x.x) } else if (x.q.hashCode() == 3739) { b.e1(b.b - x.x) } else { b.e2(b.a + x.x) }
        return K991_P(b1)
    }
}

data class B(val a: Int = 0, val b: Int = 0) { fun e1(nd: Int): B = copy(b = nd)fun e2(nd: Int): B = copy(a = nd) }

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

data class A(val q: String, val x: Int)
fun String.f(): A = split(" ".toRegex()).dropLastWhile { it.isEmpty() }.let { split -> return A(split[0], split[1].toInt()) }