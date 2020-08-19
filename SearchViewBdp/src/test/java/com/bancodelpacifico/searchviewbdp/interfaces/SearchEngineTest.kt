package com.bancodelpacifico.searchviewbdp.interfaces

import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Spy

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchEngineTest {

    @Mock
    lateinit var items :ArrayList<ItemsModel>

    @InjectMocks
    lateinit var searchEngine: SearchEngine

    lateinit var poolItems: List<ItemsModel>

    @BeforeAll
    fun setUp(){
        MockitoAnnotations.openMocks(this)

        poolItems = arrayListOf(
            ItemsModel("CATEGORY - 1","Description",type = CATEGORY, categoryId = 0),
            ItemsModel("Test One","Description","", ITEM, iconItem = R.drawable.ic_baseline_arrow_back_24, categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 0),

            ItemsModel("CATEGORY - 2","Description",type = CATEGORY,categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,iconItem = R.drawable.ic_baseline_search_24,categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 1),
            ItemsModel("Test One","Description","", ITEM,categoryId = 1)
        )

    }

    @Test
    fun searchItem() {
    }

    @Test
    fun `test for getter the category`() {
        // act
        var resultFilterFor = searchEngine.filterFor(poolItems, CATEGORY)

        //assert
        assertEquals(2, resultFilterFor.size)
    }
    @Test
    fun `test for getter the itemts`() {
        // act
        var resultFilterFor = searchEngine.filterFor(poolItems, ITEM)

        //assert
        assertEquals(6, resultFilterFor.size)
    }

    @Test
    fun `test if existed category repeated `() {
        // act
        val categores = searchEngine.filterFor(poolItems, CATEGORY)
        val listIdCategories = searchEngine.getCategoryId(categores)

        val resultRepeatedCategory = searchEngine.hasDuplicates(listIdCategories)

        //assert
        assertEquals(true, resultRepeatedCategory)
    }
}