# Android SearchViewBdp Library
 - SearchView Bdp is a library for to achive one better experiencie for the users
   and the look and feel it awesome. With this library you can  integrate easely
   and the search represented as a bar where the user can type the search result.


## Usage

Android Studio:

Add the JitPack repository if you have not already:
```
    allprojects {
        repositories { 
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
Import it as a dependency:
```
    dependencies {
    	        implementation 'com.github.buchos92:SearchViewBdp:Tag'
    	}
```

In your layout.
    One:
```
      <include layout="@layout/widget_search_bar"/>
      
      or
      
      <include
            android:id="@+id/search_view_container"
            layout="@layout/widget_search_bar"/>
     
```

Create a class Item add @Parcelize and extend of ListSearchableAbs:

@Parcelize
class Items(
    override var tittle: String,
    override var description: String?,
    override var additional: String?,
    override var type: Int
) : ListSearchableAbs()

```

```
    Activity or Fragment:
    - If your are used databinding, instead of findViewById(R.id.search_view_container) used binding.root.findViewById(R.id.search_view_container)
```
        // initialize your list of caterory and items
          val listSearchable = arrayListOf(
                  Items("CATEGORY","Description","",CATEGORY),
                  Items("Test One","Description","",ITEM),
                  Items("Test One","Description","",ITEM),
                  Items("Test One","Description","",ITEM),
                  Items("Test One","Description","",ITEM),
                  Items("CATEGORY2","Description","",CATEGORY),
                  Items("Test One","Description","",ITEM),
                  Items("Test One","Description","",ITEM),
                  Items("Test One","Description","",ITEM)
              )

        // set-up your class SearchViewBdp
        val searchViewLayout: SearchViewBdp = findViewById(R.id.search_view_container)
        searchViewLayout.setExpandedContentSupportFragment(this)

        // optiona: if you want customaize
        searchViewLayout.setCollapsedHint("Welcome to Bdp")
        searchViewLayout.setExpandedHint("Buscar...")
        searchViewLayout.setListToSearch(listSearchable)
```


License
Copyright 2020 Gabriel Torres

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
