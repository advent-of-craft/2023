export class Client {
    private totalAmount: number = 0;

    constructor(private readonly orderLines: Readonly<Record<string, number>>) {
    }

    toStatement(): string {
        const lines = Object.entries(this.orderLines).map(([name, value]) =>
            this.formatLine(name, value)
        );
        return `${lines.join('\n')}\nTotal : ${this.totalAmount.toFixed(2)}€`;
    }

    getTotalAmount(): number {
        return this.totalAmount;
    }

    private formatLine(name: string, value: number): string {
        this.totalAmount += value;
        return `${name} for ${value.toFixed(2)}€`;
    }
}
