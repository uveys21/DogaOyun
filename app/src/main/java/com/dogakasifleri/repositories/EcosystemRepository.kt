package com.dogakasifleri.repositories

import com.dogakasifleri.models.Ecosystem

interface EcosystemRepository {
    fun getEcosystemById(ecosystemId: Int): Ecosystem
}