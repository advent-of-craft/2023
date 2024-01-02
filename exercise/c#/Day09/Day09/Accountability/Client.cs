using static System.Environment;
using static System.Globalization.CultureInfo;
using static System.String;

namespace Day09.Accountability
{
    public class Client(IReadOnlyDictionary<string, double> orderLines)
    {
        private double _totalAmount;

        public string ToStatement()
            => $"{Join(
                NewLine,
                orderLines
                    .Select(kvp => FormatLine(kvp.Key, kvp.Value))
                    .ToList()
            )}{NewLine}Total : {_totalAmount.ToString(InvariantCulture)}€";

        private string FormatLine(string name, double value)
        {
            _totalAmount += value;
            return name + " for " + value.ToString(InvariantCulture) + "€";
        }

        public double TotalAmount() => _totalAmount;
    }
}