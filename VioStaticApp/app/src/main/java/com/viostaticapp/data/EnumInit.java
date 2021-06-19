package com.viostaticapp.data;

import java.util.Random;

public class EnumInit {

    public enum Table {

        YoutubeVideo("YoutubeVideo"),
        User("User");

        public String name;

        Table(String name) {
            this.name = name;
        }

    }

}
