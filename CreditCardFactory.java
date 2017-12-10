class CreditCardFactory {
    static CreditCard build(String number) {
        if (number == null)
            return new InvalidCard();

        number = number.replaceAll("\\s+","");

        if (number.matches("\\d+")) {
            int len = number.length();
            char fst = number.charAt(0);
            char sec = number.charAt(1);

            if (len == 13 && fst == '4')
                return new VisaCard(number);
            if (len == 15 && fst == '3')
                if (sec == '4' || sec == '7')
                    return new AmexCard(number);
            if (len == 16) {
                if ("6011".equals(number.substring(0,4)))
                    return new DiscoverCard(number);
                if (fst == '4')
                    return new VisaCard(number);
                if (fst == '5')
                    if (sec == '1' || sec == '2' || sec == '3' || sec == '4' || sec == '5')
                        return new MasterCard(number);
            }
        }
        return new InvalidCard();
    }
}
