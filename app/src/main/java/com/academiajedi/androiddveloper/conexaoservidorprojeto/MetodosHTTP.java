package com.academiajedi.androiddveloper.conexaoservidorprojeto;

/**
 * Created by alexsoaresdesiqueira on 10/02/17.
 */

public enum MetodosHTTP {

    GET         ("Get"),
    POST        ("Post");

    private final String name;

    MetodosHTTP(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}
