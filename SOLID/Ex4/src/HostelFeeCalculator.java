import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final List<RoomPricing> roomPricings;
    private final List<AddOnPricing> addOnPricings;

    public HostelFeeCalculator(FakeBookingRepo repo) {
        this.repo = repo;
        this.roomPricings = List.of(
                new StandardRoomPricing(LegacyRoomTypes.SINGLE, 14000.0),
                new StandardRoomPricing(LegacyRoomTypes.DOUBLE, 15000.0),
                new StandardRoomPricing(LegacyRoomTypes.TRIPLE, 12000.0),
                new StandardRoomPricing(LegacyRoomTypes.DELUXE, 16000.0));
        this.addOnPricings = List.of(
                new StandardAddOnPricing(AddOn.MESS, 1000.0),
                new StandardAddOnPricing(AddOn.LAUNDRY, 500.0),
                new StandardAddOnPricing(AddOn.GYM, 300.0));
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        double base = roomPricings.stream()
                .filter(r -> r.supports(req.roomType))
                .mapToDouble(RoomPricing::monthlyBase)
                .findFirst()
                .orElse(16000.0);

        double addOnTotal = req.addOns.stream()
                .mapToDouble(addOn -> addOnPricings.stream()
                        .filter(p -> p.supports(addOn))
                        .mapToDouble(AddOnPricing::monthlyCharge)
                        .findFirst()
                        .orElse(0.0))
                .sum();

        return new Money(base + addOnTotal);
    }
}
