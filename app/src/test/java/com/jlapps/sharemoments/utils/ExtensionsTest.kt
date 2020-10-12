package com.jlapps.sharemoments.utils

import org.junit.Assert.*
import org.junit.Test

class ExtensionsTest {

    @Test
    fun longToDate_returnsDateAsString() {

        // When extension fun is called in a long date
        val currentDate = 1602534807597
        val stringDate = currentDate.toDate()

        // Then the date should be an String
        assertEquals("12/10/2020", stringDate)
    }
}