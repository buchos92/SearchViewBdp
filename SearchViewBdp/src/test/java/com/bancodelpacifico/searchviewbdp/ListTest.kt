package com.bancodelpacifico.searchviewbdp

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ListTest {
    @Mock
    private lateinit var testMock: List<String>

    //@InjectMocks // class with dependency

    @BeforeAll
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `verify`(){
        val mockitoList = mock(List::class.java)

       /* verify(mockitoList).size

        verify(mockitoList, times(3)).size

        verifyNoInteractions(mockitoList)

        verifyNoMoreInteractions(mockitoList)

        verify(mockitoList, times(0)).size

        verify(mockitoList, never()).size

        verify(mockitoList, atMostOnce()).size

        verify(mockitoList, atMost(3)).size

        val inOrder = inOrder(mockitoList)
        inOrder.verify(mockitoList).size
        inOrder.verify(mockitoList)[ArgumentMatchers.anyInt()]
        inOrder.verify(mockitoList).contains("Test")*/
    }

    @Test
    fun `verify 3 test `(){

        `when`(testMock.size).thenReturn(0)
    }
}