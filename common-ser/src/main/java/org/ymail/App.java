package org.ymail;

import org.ymail.enums.EmailStatus;

public class App {
    public static void main(String[] args) {
        System.out.println(EmailStatus.getValueByKey(1));

    }
}
