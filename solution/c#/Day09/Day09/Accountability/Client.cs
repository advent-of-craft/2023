using static System.Environment;
using static System.Globalization.CultureInfo;
using static System.String;

namespace Day09.Accountability
{
    public class Client(IReadOnlyDictionary<string, double> orderLines)
    {
        public string ToStatement()
            => $"{Join(
                NewLine,
                orderLines.Select(kvp => FormatLine(kvp.Key, kvp.Value))
            )}{FormatTotal()}";

        private static string FormatLine(string name, double value)
            => $"{name} for {value.ToString(InvariantCulture)}€";

        private string FormatTotal() => $"{NewLine}Total : {TotalAmount().ToString(InvariantCulture)}€";
        public double TotalAmount() => orderLines.Values.Sum();
    }
}