package me.dionclei.url_shortener.exceptions;

import java.time.Instant;
/**
 * This object is what the exceptions will return
 */
public record StandardError(Instant timestamp, Integer status, String error, String message, String path) {

}
