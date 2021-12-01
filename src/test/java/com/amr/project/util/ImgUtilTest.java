package com.amr.project.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImgUtilTest {

    @Test
    void toByteArr() {
        assertEquals(0, ImgUtil.toByteArr("dir/subdir/img/filename.jpg").length);
        assertTrue(ImgUtil.toByteArr("src/main/resources/static/img/logo/logo.png").length > 0);
        assertThrows(NullPointerException.class, () -> ImgUtil.toByteArr(null));
    }

    @Test
    void toBase64img() {
        String base64 = ImgUtil.toBase64img(
                "src/main/resources/static/img/logo/logo.png",
                ImgUtil.toByteArr("src/main/resources/static/img/logo/logo.png")
        );
        assertTrue(base64.contains("data:image/png;base64,"));
    }
}