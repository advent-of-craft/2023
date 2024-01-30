type Maybe<T> = NonNullable<T> | null;

export class Food {
    constructor(
        public readonly expirationDate: Date,
        public readonly approvedForConsumption: boolean,
        public readonly inspectorId: Maybe<string>
    ) {
    }

    isEdible(now: () => Date): boolean {
        return this.expirationDate > now() &&
            this.approvedForConsumption &&
            this.inspectorId !== null;
    }
}