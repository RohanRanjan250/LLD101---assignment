public class StandardRoomPricing implements RoomPricing {
    private final int roomType;
    private final double base;

    public StandardRoomPricing(int roomType, double base) {
        this.roomType = roomType;
        this.base = base;
    }

    @Override
    public boolean supports(int type) {
        return this.roomType == type;
    }

    @Override
    public double monthlyBase() {
        return base;
    }
}
