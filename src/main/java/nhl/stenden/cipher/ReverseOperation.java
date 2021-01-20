package nhl.stenden.cipher;

public class ReverseOperation implements CipherOperation {

    @Override
    public String decipher(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
