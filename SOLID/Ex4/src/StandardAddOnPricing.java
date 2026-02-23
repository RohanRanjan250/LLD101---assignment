public class StandardAddOnPricing implements AddOnPricing {
    private final AddOn addOn;
    private final double charge;

    public StandardAddOnPricing(AddOn addOn, double charge) {
        this.addOn = addOn;
        this.charge = charge;
    }

    @Override
    public boolean supports(AddOn a) {
        return this.addOn == a;
    }

    @Override
    public double monthlyCharge() {
        return charge;
    }
}
