package com.dogakasifleri.repositories

import com.dogakasifleri.models.Species

interface SpeciesRepository {
    fun getSpeciesByEcosystemId(ecosystemId: Int): List<Species>
}