package com.viostaticapp.data;

public class EnumInit {

    public enum Collections {

        YoutubeVideo("YoutubeVideo"),
        User("User");

        public String name;

        Collections(String name) {
            this.name = name;
        }

    }

}
