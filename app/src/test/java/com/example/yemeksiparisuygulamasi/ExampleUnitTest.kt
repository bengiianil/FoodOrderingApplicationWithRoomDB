package com.example.yemeksiparisuygulamasi

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(129.72f, calculateProductSumPrice(32.43f,4))
    }

    private fun calculateProductSumPrice(foodPrice: Float, quantity:Int): Float{
        return foodPrice * quantity
    }
}