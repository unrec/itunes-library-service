package com.unrec.ituneslibrary.validation;

import com.unrec.ituneslibrary.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTrack;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RatingValidatorTest {

    private RatingValidator validator = new RatingValidator();
    private static Track track;

    @BeforeEach
    void setUp() {
        track = getTestTrack();
    }

    @Test
    void isNotValid_whenRatingIsNotSet() {
        track.setRating(0);
        assertTrue(validator.isValid(track.getRating(), null));
    }

    @Test
    void isNotValid_whenRatingIsMax() {
        track.setRating(100);
        assertTrue(validator.isValid(track.getRating(), null));
    }

    @Test
    void isNotValid_whenRatingIsOutOfBounds() {
        track.setRating(200);
        assertFalse(validator.isValid(track.getRating(), null));
    }

    @Test
    void isNotValid_whenRatingIsNegative() {
        track.setRating(-100);
        assertFalse(validator.isValid(track.getRating(), null));
    }
}