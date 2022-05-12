package org.spring.notice.domain.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.spring.notice.domain.user.User;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PostTest {

    private final User user = User.create(UUID.randomUUID().toString(), "테스트유저", 20, "코딩..이랄까");

    @ParameterizedTest
    @NullAndEmptySource
    void 제목은_공백일수_없음(String title) {
        assertThatIllegalStateException().isThrownBy(
                () -> Post.write(title, "aaaa", user)
        );
    }

    @Test
    void 작성자는_필수(){
        assertThatNullPointerException().isThrownBy(
                () -> Post.write("테스트 제목", "aaaa", null)
        );
    }
}