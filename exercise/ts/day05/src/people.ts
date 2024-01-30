export enum PetType {
    Cat,
    Dog,
    Hamster,
    Turtle,
    Bird,
    Snake
}

export class Pet {
    constructor(
        public readonly type: PetType,
        public readonly name: string,
        public readonly age: number
    ) {
    }
}

export class Person {
    constructor(
        public readonly firstName: string,
        public readonly lastName: string,
        public readonly pets: Pet[]
    ) {
    }
}
