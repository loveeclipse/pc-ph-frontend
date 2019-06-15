package it.unibo.preh_frontend.model

data class HistoryData<PreHData>(
        val event: String,
        val eventData: PreHData,
        val eventTime: String
)