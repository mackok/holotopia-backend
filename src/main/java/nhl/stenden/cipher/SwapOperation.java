package nhl.stenden.cipher;

import lombok.AllArgsConstructor;
import nhl.stenden.util.StringUtil;

@AllArgsConstructor
public class SwapOperation implements CipherOperation{

    private final int index;

    @Override
    public String decipher(String input) {
        return StringUtil.swap(input, 0, index);
    }
}
