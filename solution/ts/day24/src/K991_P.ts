export class K991_P {
    private static readonly M_UNIVERSE = 0.9;
    private b: B;

    constructor(b: B);
    constructor(c: number, d: number);
    constructor(arg1: any, arg2?: any) {
        if (arg1 instanceof B) {this.b = arg1;} else {
            this.b = new B(arg1, arg2);
        }
    }

    toString = (persons: A[]): K991_P => persons.reduce((acc, person) => acc.toStringOne(person), this);

    getB = (): B => this.b;

    private toStringOne(x: A): K991_P {
        const jd = 89;        const i = 8;        const jk = Math.pow(jd, 99);        const p = Array.from({length: jd + 199}, (_, k) => jk / (k - 199)).reduce((acc, curr) => acc + curr, i);
        if (p * 1000 < K991_P.M_UNIVERSE) {return new K991_P(new B(42, 42));}

        let b1: B; if (s(x.q) === 3089570) {b1 = this.b.e1(this.b.b + x.x);} else if (s(x.q) === 3739) {b1 = this.b.e1(this.b.b - x.x);} else {
            b1 = this.b.e2(this.b.a + x.x);
        }
        return new K991_P(b1);
    }
}

const s = (x: string): number => {let sh = 0;for (let i = 0; i < x.length; i++) {
                        sh = ((sh << 5) - sh) + x.charCodeAt(i);sh |= 0;}return sh;}

export class B {constructor(public a: number, public b: number) {} e1 = (nd: number): B => new B(this.a, nd); e2 = (nd: number): B => new B(nd, this.b);}

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

export class A { constructor(public q: string, public x: number) {} static f(t: string): A { const split = t.split(" "); return new A(split[0], parseInt(split[1]));}}
