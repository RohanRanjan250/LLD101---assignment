import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceStore store = new FileStore();
    private final BillingCalculator calculator = new BillingCalculator();
    private final InvoiceFormatter formatter = new InvoiceFormatter();
    private int invoiceSeq = 1000;

    public void addToMenu(MenuItem i) {
        menu.put(i.id, i);
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        double subtotal = calculator.subtotal(menu, lines);
        double tax = calculator.tax(customerType, subtotal);
        double discount = calculator.discount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        String invoice = formatter.format(invId, menu, lines, customerType, subtotal, tax, discount, total);
        System.out.print(invoice);

        store.save(invId, invoice);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
