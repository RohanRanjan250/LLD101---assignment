import java.util.*;

public class BillingCalculator {
    public double subtotal(Map<String, MenuItem> menu, List<OrderLine> lines) {
        double sub = 0.0;
        for (OrderLine l : lines) {
            sub += menu.get(l.itemId).price * l.qty;
        }
        return sub;
    }

    public double tax(String customerType, double subtotal) {
        return subtotal * TaxRules.taxPercent(customerType) / 100.0;
    }

    public double discount(String customerType, double subtotal, int lineCount) {
        return DiscountRules.discountAmount(customerType, subtotal, lineCount);
    }
}
