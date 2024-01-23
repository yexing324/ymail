public class test {
    public static void main(String[] args) {
        String in="sda  d?/= <284848@qq.com> ";
        String[] split = in.split("[@>]");
        System.out.println(split[1]);
    }
}
