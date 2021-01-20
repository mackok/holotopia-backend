package nhl.stenden.util;

public class StringUtil extends UtilClass {

    public static String swap(String string, int i, int j){
        char[] ch = string.toCharArray();
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
        return new String(ch);
    }
}
