package dev.shnhs.kotboard.domain

import dev.shnhs.kotboard.exception.PostNotUpdatableException
import dev.shnhs.kotboard.service.dto.PostUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Post(
    createdBy: String,
    title: String,
    content: String,
) : BaseEntity(
        createdBy = createdBy,
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set
    var content: String = content
        protected set

    fun update(requestDto: PostUpdateRequestDto) {
        if (requestDto.updatedBy != this.createdBy) {
            throw PostNotUpdatableException()
        }
        this.title = requestDto.title
        this.content = requestDto.content
        super.updatedAt = LocalDateTime.now()
        super.updatedBy = requestDto.updatedBy
    }
}
