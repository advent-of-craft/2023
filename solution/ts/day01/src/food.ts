type Maybe<T> = NonNullable<T> | null;
type DateProvider = () => Date;

export class Food {
    constructor(
        public readonly expirationDate: Date,
        public readonly approvedForConsumption: boolean,
        public readonly inspectorId: Maybe<string>
    ) {
    }

    isEdible(now: DateProvider): boolean {
        return this.isFresh(now) &&
            this.canBeConsumed() &&
            this.hasBeenInspected();
    }

    private isFresh = (now: () => Date) => this.expirationDate > now();
    private canBeConsumed = () => this.approvedForConsumption;
    private hasBeenInspected = () => this.inspectorId !== null;
}