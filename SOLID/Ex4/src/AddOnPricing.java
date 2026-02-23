public interface AddOnPricing {
    boolean supports(AddOn addOn);

    double monthlyCharge();
}
