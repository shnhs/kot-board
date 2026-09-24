package dev.shnhs.kotboard.service

import dev.shnhs.kotboard.domain.Post
import dev.shnhs.kotboard.exception.PostNotDeletableException
import dev.shnhs.kotboard.exception.PostNotFoundException
import dev.shnhs.kotboard.exception.PostNotUpdatableException
import dev.shnhs.kotboard.repository.PostRepository
import dev.shnhs.kotboard.service.dto.PostCreateRequestDto
import dev.shnhs.kotboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    @Autowired private val postService: PostService,
    @Autowired private val postRepository: PostRepository,
    repository: PostRepository,
    service: PostService,
) : BehaviorSpec({
        given("게시글 생성요청 시") {
            When("정상적인 요청이라면") {
                val postId =
                    postService.createPost(
                        PostCreateRequestDto(
                            title = "제목",
                            content = "내용",
                            createdBy = "shnhs",
                        ),
                    )
                then("게시글이 정상적으로 생성됨을 확인한다.") {
                    postId shouldBeGreaterThan 0L
                    val post = postRepository.findByIdOrNull(postId)
                    post.shouldNotBeNull()
                    post.title shouldBe "제목"
                    post.content shouldBe "내용"
                }
            }
        }

        given("게시글 수정 시") {
            val saved =
                postRepository.save(Post(title = "title", content = "content", createdBy = "shnhs"))
            When("정상적인 수정 요청 시") {
                val updatedId =
                    postService.updatePost(
                        saved.id,
                        PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "shnhs",
                        ),
                    )
                then("정상적으로 게시글이 수정됨을 확인") {
                    saved.id shouldBe updatedId

                    val updated = postRepository.findByIdOrNull(updatedId)
                    updated.shouldNotBeNull()
                    updated.title shouldBe "update title"
                    updated.content shouldBe "update content"
                }
            }

            When("게시글이 없을 때") {
                then("게시글을 찾을 수 없는 예외 발생") {
                    shouldThrow<PostNotFoundException> {
                        postService.updatePost(
                            999L,
                            PostUpdateRequestDto(
                                title = "update title",
                                content = "update content",
                                updatedBy = "update shnhs",
                            ),
                        )
                    }
                }
            }

            When("작성자가 동일하지 않으면") {
                then("수정할 수 없다는 예외가 발생") {
                    shouldThrow<PostNotUpdatableException> {
                        postService.updatePost(
                            1L,
                            PostUpdateRequestDto(
                                title = "update title",
                                content = "update content",
                                updatedBy = "other shnhs",
                            ),
                        )
                    }
                }
            }
        }

        given("게시글 삭제 시") {

            When("정상 삭제 요청 시") {
                val saved =
                    postRepository.save(
                        Post(title = "title", content = "content", createdBy = "shnhs"),
                    )
                val deletedPostId = postService.deletePost(saved.id, "shnhs")
                then("게시글이 정상적으로 삭제됨") {
                    deletedPostId shouldBe saved.id
                    postRepository.findByIdOrNull(deletedPostId).shouldBeNull()
                }
            }
            When("작성자가 아니라면") {
                val otherPost =
                    postRepository.save(
                        Post(title = "title", content = "content", createdBy = "shnhs"),
                    )
                then("삭제할 수 없다고 예외 발생") {
                    shouldThrow<PostNotDeletableException> {
                        postService.deletePost(
                            otherPost.id,
                            "other shnhs",
                        )
                    }
                }
            }
        }
    })
