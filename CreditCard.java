abstract class CreditCard {
    String number;

    CreditCard(String number) {
        this.number = number;
    }

    String getNumber() {
        return number;
    }

    abstract String getName();

    void charge(double amount) {}
}
class MasterCard extends CreditCard {
    MasterCard(String number) {
        super(number);
    }

    String getName() {
        return "MasterCard";
    }
}
class VisaCard extends CreditCard {
    VisaCard(String number) {
        super(number);
    }

    String getName() {
        return "Visa";
    }
}

class AmexCard extends CreditCard {
    AmexCard(String number) {
        super(number);
    }

    String getName() {
        return "American Express";
    }
}
class DiscoverCard extends CreditCard {
    DiscoverCard(String number) {
        super(number);
    }

    String getName() {
        return "Discover";
    }
}
class InvalidCard extends CreditCard {
    InvalidCard() {
        super(null);
    }

    String getName() {
        return "Invalid card type.";
    }
}