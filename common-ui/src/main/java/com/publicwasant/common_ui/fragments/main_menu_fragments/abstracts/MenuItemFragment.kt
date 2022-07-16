package com.publicwasant.common_ui.fragments.main_menu_fragments

import androidx.fragment.app.Fragment

abstract class MainMenuFragment: Fragment() {
    abstract fun onRefresh(callback: () -> Unit)
}