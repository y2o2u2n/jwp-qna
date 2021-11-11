package subway.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@DataJpaTest
class LineTest {
	@Autowired
	private LineRepository lines;

	@Autowired
	private StationRepository stations;

	@Test
	void saveWithLine_fail() {
		// given
		Station expected = new Station("잠실역");
		expected.setLine(new Line("2호선"));

		// when
		Station actual = stations.save(expected);

		// then
		assertThatThrownBy(() -> stations.flush()) // 실무에서는 잘 쓰지 않지만 확인하기 위해서 씀...
			.isInstanceOf(InvalidDataAccessApiUsageException.class);

		// JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.
	}

	@Test
	void saveWithLine() {
		// given
		Station expected = new Station("잠실역");
		expected.setLine(lines.save(new Line("2호선")));

		// when
		Station actual = stations.save(expected);

		// then
		stations.flush(); // 실무에서는 잘 쓰지 않지만 확인하기 위해서 씀...
	}

	@Test
	void findByNameWithLine() {
		Station actual = stations.findByName("교대역");
		assertThat(actual).isNotNull();
		assertThat(actual.getLine().getName()).isEqualTo("3호선"); // 연관 관계로 추가 로딩을 한다.
	}

	@Test
	void updateWithLine() {
		Station station = stations.findByName("교대역");
		station.setLine(lines.save(new Line("2호선")));
		stations.flush(); // 라인을 변경해서 연관 관계를 변경할 수 있다.
	}

	@Test
	void removeLine() {
		Station station = stations.findByName("교대역");
		station.setLine(null);
		stations.flush(); // 연관관계를 끊을 수 있다. 물론, 기존 라인이 지워지는 건 아니다.

		// 만약 노선 자체를 완전 지우려면 연관된 역과의 연관 관계를 모두 끊어낸 뒤에 삭제를 해야 한다.
	}
}
