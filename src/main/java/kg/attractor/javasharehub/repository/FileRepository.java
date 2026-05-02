package kg.attractor.javasharehub.repository;

import kg.attractor.javasharehub.model.FileEntity;
import kg.attractor.javasharehub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Page<FileEntity> findAllByIsPublicTrue(Pageable pageable);
    Page<FileEntity> findAllByIsPublicTrueAndCategory(String category, Pageable pageable);

    List<FileEntity> findAllByUploader(User uploader);

    Optional<FileEntity> findByUniqueKey(String uniqueKey);
}