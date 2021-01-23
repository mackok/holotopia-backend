package nhl.stenden.cipher;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SliceOperation implements CipherOperation {

    private final int index;

    @Override
    public String decipher(String input) {
        return input.substring(index);
    }
}
