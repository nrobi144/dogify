package com.nagyrobi144.dogify.usecase

import com.nagyrobi144.dogify.repository.BreedsRepository
import com.nagyrobi144.dogify.util.Result

class FetchBreedsUseCase(private val repository: BreedsRepository) {

    suspend operator fun invoke() = Result {
        val breeds = repository.fetch()
        isPartial = breeds.any { it == null }
        breeds.filterNotNull()
    }
}