package org.chrivin.hsrcomposeapp.di

import org.chrivin.hsrcomposeapp.data.HSRCharacterRepo

object Injection {
    fun provideRepository(): HSRCharacterRepo {
        return HSRCharacterRepo.getInstance()
    }
}