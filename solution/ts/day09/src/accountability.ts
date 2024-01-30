export class Client {
    constructor(private readonly orderLines: Record<string, number>) {
    }

    statement = (): string => `${Object.entries(this.orderLines)
        .map(
            ([key, value]) => this.formatLine(key, value)
        ).join('\n')}\n${this.formatTotal()}`;

    totalAmount = (): number => Object
        .values(this.orderLines)
        .reduce((sum, value) => sum + value, 0);

    private formatLine = (name: string, value: number): string => `${name} for ${this.formatAmount(value)}`;
    private formatTotal = (): string => `Total : ${this.formatAmount(this.totalAmount())}`;
    private formatAmount = (amount: number): string => `${amount.toFixed(2)}€`;
}