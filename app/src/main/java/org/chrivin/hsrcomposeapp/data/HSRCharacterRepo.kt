package org.chrivin.hsrcomposeapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.chrivin.hsrcomposeapp.model.HSRCharacter
import org.chrivin.hsrcomposeapp.model.HSRCharacterData

class HSRCharacterRepo {
    private val dummyHSRCharacter = mutableListOf<HSRCharacter>()

    init {
        if (dummyHSRCharacter.isEmpty()) {
            HSRCharacterData.dummyHSRCharacter.forEach {
                dummyHSRCharacter.add(it)
            }
        }
    }

    fun getHSRCharaById(characterId: Int): HSRCharacter {
        return dummyHSRCharacter.first {
            it.id == characterId
        }

    }

    fun searchCharacter(query: String) = flow {
        val result = mutableListOf<HSRCharacter>()
        dummyHSRCharacter.filterTo(result) {
            it.name.contains(query, ignoreCase = true)
        }
        emit(result)
    }

    fun getFavoriteHSRChara() : Flow<List<HSRCharacter>> {
        return flowOf(dummyHSRCharacter.filter { it.isFavorite })
    }


    fun updateHSRChara(characterId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyHSRCharacter.indexOfFirst { it.id == characterId }
        val result = if (index >= 0) {
            val hsrCharacter = dummyHSRCharacter[index]
            dummyHSRCharacter[index] = hsrCharacter.copy(isFavorite = newState)
            true // Success update Character
        } else {
            false // Id not found , fail to update
        }
        return flowOf(result)
    }


    companion object {
        @Volatile
        private var instance: HSRCharacterRepo? = null

        fun getInstance(): HSRCharacterRepo =
            instance ?: synchronized(this) {
                HSRCharacterRepo().apply {
                    instance = this
                }
            }
    }
}