import java.util.*;

public class InvoiceFormatter {
    public String format(String invId, Map<String, MenuItem> menu,
            List<OrderLine> lines, String customerType,
            double subtotal, double tax, double discount, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice# ").append(invId).append("\n");
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            sb.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, item.price * l.qty));
        }
        double taxPct = TaxRules.taxPercent(customerType);
        sb.append(String.format("Subtotal: %.2f\n", subtotal));
        sb.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
        sb.append(String.format("Discount: -%.2f\n", discount));
        sb.append(String.format("TOTAL: %.2f\n", total));
        return sb.toString();
    }
}
