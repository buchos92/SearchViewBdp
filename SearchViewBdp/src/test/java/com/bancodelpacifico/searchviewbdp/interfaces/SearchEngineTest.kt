package com.bancodelpacifico.searchviewbdp.interfaces

import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM_SECOND_OPTION
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchEngineTest {

    @Mock
    lateinit var items :ArrayList<ItemsModel>

    @InjectMocks
    lateinit var searchEngine: SearchEngine

    private lateinit var poolItems: MutableList<ItemsModel>

    @BeforeAll
    fun setUp(){
        MockitoAnnotations.openMocks(this)

      /*  poolItems = mutableListOf(
            ItemsModel("CATEGORY - 1","Description",type = CATEGORY, categoryId = 0),
            ItemsModel("Test One","Description","", ITEM, iconItem = R.drawable.ic_baseline_arrow_back_24, categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 0),

            ItemsModel("CATEGORY - 2","Description",type = CATEGORY,categoryId = 6),
            ItemsModel("Test One","Description","", ITEM,iconItem = R.drawable.ic_baseline_search_24,categoryId = 0),
            ItemsModel("Test One","Description","", ITEM,categoryId = 6),
            ItemsModel("Marcos","Description","", ITEM,categoryId = 6)
        )*/

        poolItems = mutableListOf(
            ItemsModel("CATEGORY - 1","Description",type = CATEGORY , categoryId = 0),
            ItemsModel("Manuel","Marcos","",type = ITEM , iconItem = R.drawable.ic_baseline_arrow_back_24, categoryId = 0),
            ItemsModel("Gabriel","Description","",type = ITEM ,categoryId = 0),
            ItemsModel("Marcos","Gabriel","",type = ITEM,categoryId = 0),

            ItemsModel("CATEGORY - 2","Description",type = CATEGORY,categoryId = 1),
            ItemsModel("Gabriel","Manuel","",type = ITEM,iconItem = R.drawable.ic_baseline_search_24,categoryId = 0),
            ItemsModel("Manuel One","Description","",type = ITEM,categoryId = 1),
            ItemsModel("Marcos","fffff","",type = ITEM,categoryId = 1)
        )
    }

    @Test
    fun `test for getter the category`() {
        // act
        val resultFilterFor = searchEngine.filterFor(poolItems, CATEGORY, null)

        //assert
        assertEquals(2, resultFilterFor.size)
    }
    @Test
    fun `test for getter the itemts`() {
        // act
        val resultFilterFor = searchEngine.filterFor(poolItems, ITEM, ITEM_SECOND_OPTION)

        //assert
        assertEquals(6, resultFilterFor.size)
    }

    @Test
    fun `test if existed category repeated `() {
        // act
        val categories = searchEngine.filterFor(poolItems, CATEGORY,null)
        val listIdCategories = searchEngine.getCategoryId(categories)

        val resultRepeatedCategory = searchEngine.hasDuplicates(listIdCategories)

        //assert
        assertEquals(false, resultRepeatedCategory)
    }

    //@Test
    fun `test search for letter `() {
        // act
        val phrases = "gabriel"
        searchEngine.setItemsModel(poolItems)

        val result = searchEngine.searchItem(phrases)

        //assert
        assertEquals(8, result.size)
    }
}