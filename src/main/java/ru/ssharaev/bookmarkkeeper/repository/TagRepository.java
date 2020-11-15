package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssharaev.bookmarkkeeper.model.Tag;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    default Tag findOrCreate(String name) {
        checkNotNull(name);
        Optional<Tag> result = findByName(name);
        if (result.isEmpty()) {
            Tag tag = Tag.builder().name(name).build();
            return save(tag);
        }
        return result.get();
    }
}
