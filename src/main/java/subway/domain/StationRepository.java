package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository는 @Repository를 붙이지 않아도 빈으로 등록해준다.
public interface StationRepository extends JpaRepository<Station, Long> {
	Station findByName(String name);
}
