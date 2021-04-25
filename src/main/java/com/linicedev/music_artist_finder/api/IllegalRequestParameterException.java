package com.linicedev.music_artist_finder.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
public class IllegalRequestParameterException extends RuntimeException {
    private static final long serialVersionUID = -1025208129246749101L;

    public IllegalRequestParameterException(String message) {
        super(message);
    }
}
