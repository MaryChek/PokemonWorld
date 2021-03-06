package com.example.work_with_service.ui.contract

interface PagerPokemonContract {
    interface View {
        fun setTitleByPosition(position: Int, title: String)

        fun selectItemOnPager(position: Int)

        fun disableSwapPage()

        fun disableOnBackPressedCallback()

        fun onBackPressed()
    }

    interface Presenter {
        fun onPageSelected(position: Int)

        fun onDetailPageOpens(namePokemon: String)

        fun onPageStartedScrolling(position: Int)

        fun onBackPressed(pagePosition: Int)
    }
}