package com.interview.questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class EasyQuestionsTests {

    @Test
    public void primitivesByValue(){
        int first = 1;
        int second = 1;
        // ==
        Assertions.assertSame(first, second);
        // equals
        Assertions.assertEquals(first, second);
    }

    @Test
    public void objectsByValue(){
        Integer first = 127;
        Integer second = 127;

        // jvm hack for cashing small values [-128...127]
        Assertions.assertSame(first, second);
        Assertions.assertEquals(first, second);

        first = 128;
        second = 128;
        Assertions.assertNotSame(first, second);
        Assertions.assertEquals(first, second);

        // Boolean constructor is deprecated
        Assertions.assertNotSame(new Boolean(false), Boolean.FALSE);
        Assertions.assertSame(false, Boolean.FALSE);
        Assertions.assertEquals(false, Boolean.FALSE);
    }

    @Test
    public void mutateHashMapKey(){
        // regular map
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 1L);
        Long val = map.get(1L);
        Assertions.assertEquals(1L, val);

        // map with key is mutable object
        Map<Map<Long, Long>, Long> multiMap = new HashMap<>();
        multiMap.put(map, 2L);

        // build new object to get value => works
        Map<Long, Long> mapKey = new HashMap<>();
        mapKey.put(1L, 1L);
        Long objectValue = multiMap.get(mapKey);
        Assertions.assertEquals(2L, objectValue);

        // mutate initial map and try to get => not work
        map.put(2L, 1L);
        Long mutable = multiMap.get(map);
        Assertions.assertNotEquals(2L, mutable);

        // try again with the same object key => not work
        Long tryAgain = multiMap.get(mapKey);
        Assertions.assertNotEquals(2L, tryAgain);

        // try again with modified object key => not work
        mapKey.put(2L, 1L);
        Long tryAgainMutated = multiMap.get(mapKey);
        Assertions.assertNotEquals(2L, tryAgainMutated);

        // Ничего не работает т.к. кэшируются хэшкоды до перераспределения по buckets
    }

}
