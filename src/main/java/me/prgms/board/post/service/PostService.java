package me.prgms.board.post.service;

import me.prgms.board.domain.post.Post;
import me.prgms.board.post.converter.PostConverter;
import me.prgms.board.post.dto.CreatePostDto;
import me.prgms.board.post.dto.ResponsePostDto;
import me.prgms.board.post.dto.UpdatePostDto;
import me.prgms.board.post.repository.PostRepository;
import me.prgms.board.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostConverter postConverter, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
        this.userRepository = userRepository;
    }

    public Long create(CreatePostDto postDto) {
        findUser(postDto.getUserDto().getId());

        Post post = postConverter.postDtoToPost(postDto);
        Post save = postRepository.save(post);
        return save.getId();
    }

    public Long update(Long postId, UpdatePostDto postDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Id로 조회되는 게시글이 없음"));

        findUser(post.getUser().getId());

        post.changePost(postDto.getTitle(), postDto.getContent());
        return postId;
    }

    public ResponsePostDto findPostById(Long postId) {
        return postRepository.findById(postId)
                .map(postConverter::postToPostDto)
                .orElseThrow(() -> new IllegalArgumentException("Id로 조회되는 게시글이 없음"));
    }

    public Page<ResponsePostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::postToPostDto);
    }

    private void findUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Id로 조회되는 유저가 없음"));
    }

}
