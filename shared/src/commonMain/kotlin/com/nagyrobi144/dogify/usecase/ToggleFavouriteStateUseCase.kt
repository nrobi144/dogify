package com.nagyrobi144.dogify.usecase

import com.nagyrobi144.dogify.repository.BreedsRepository
import com.nagyrobi144.dogify.repository.model.Breed
import com.nagyrobi144.dogify.util.Result

class ToggleFavouriteStateUseCase(private val repository: BreedsRepository) {

    suspend operator fun invoke(breed: Breed) = Result {
        repository.update(breed.copy(isFavourite = !breed.isFavourite))
    }
}