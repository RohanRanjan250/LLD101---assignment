public interface RoomPricing {
    boolean supports(int roomType);

    double monthlyBase();
}
