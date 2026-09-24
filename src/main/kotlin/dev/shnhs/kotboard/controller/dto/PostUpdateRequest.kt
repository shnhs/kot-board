package dev.shnhs.kotboard.controller.dto

import dev.shnhs.kotboard.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
)

fun PostUpdateRequest.toDto() =
    PostUpdateRequestDto(
        title = title,
        content = content,
        updatedBy = updatedBy,
    )
