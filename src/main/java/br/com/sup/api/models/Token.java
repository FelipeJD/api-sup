package br.com.sup.api.models;

public record Token(
    String token,
    String type,
    String prefix,
    Long id
) {
}
