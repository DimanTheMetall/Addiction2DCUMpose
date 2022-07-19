package com.example.addiction2dcumpose.dataClasses

class RandomScreenButtonState(val isBackButtonActive: Boolean, val isNextButtonActive: Boolean) {
    companion object StateGenerator {
        fun generateButtonsState(
            list: List<Any>,
            currentIndex: Int,
            status: MangaResult
        ): RandomScreenButtonState {
            if (status == MangaResult.Progress) {
                return RandomScreenButtonState(
                    isBackButtonActive = false,
                    isNextButtonActive = false
                )
            } else if (list.isEmpty()) {
                return RandomScreenButtonState(
                    isBackButtonActive = false,
                    isNextButtonActive = true
                )
            } else {
                when (currentIndex) {
                    0 -> {
                        return RandomScreenButtonState(isBackButtonActive = false,
                            isNextButtonActive = true
                        )
                    }
                    list.lastIndex -> {
                        return RandomScreenButtonState(isBackButtonActive = true, isNextButtonActive = true)
                    }
                    else -> {
                        return RandomScreenButtonState(isBackButtonActive = true,
                            isNextButtonActive = true
                        )
                    }
                }
            }
        }
    }
}
