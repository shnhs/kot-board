package dev.shnhs.kotboard.service.dto

import dev.shnhs.kotboard.domain.Post

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateRequestDto.toEntity() =
    Post(
        title = title,
        content = content,
        createdBy = createdBy,
    )
