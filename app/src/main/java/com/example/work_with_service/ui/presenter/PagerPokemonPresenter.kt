package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.contract.PagerPokemonContract
import com.example.work_with_service.ui.model.PagerTitlesModel

class PagerPokemonPresenter(
    private val model: PagerTitlesModel,
    private val view: PagerPokemonContract.View
) : PagerPokemonContract.Presenter {

    override fun onPageSelected(position: Int) {
        model.getTitleByPosition(position)?.let {
            view.setTitleByPosition(position, it)
        }
    }

    override fun onPageStartedScrolling(position: Int) {
        if (position == POKEMON_LIST_PAGE_POSITION) {
            view.disableSwapPage()
        }
    }

    override fun onBackPressed(pagePosition: Int) {
        if (pagePosition == DETAIL_PAGE_POSITION) {
            view.selectItemOnPager(POKEMON_LIST_PAGE_POSITION)
        } else {
            view.disableOnBackPressedCallback()
            view.onBackPressed()
        }
    }

    override fun onDetailPageOpens(namePokemon: String) {
        model.setTitleByPosition(DETAIL_PAGE_POSITION, namePokemon)
        view.selectItemOnPager(DETAIL_PAGE_POSITION)
    }

    companion object {
        private const val POKEMON_LIST_PAGE_POSITION = 0
        private const val DETAIL_PAGE_POSITION = 1
    }
}